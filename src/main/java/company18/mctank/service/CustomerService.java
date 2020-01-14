package company18.mctank.service;

import company18.mctank.domain.Customer;
import company18.mctank.domain.CustomerRoles;
import company18.mctank.domain.Discount;
import company18.mctank.exception.AnonymusUserException;
import company18.mctank.exception.ExistedUserException;
import company18.mctank.exception.UserNotFoundException;
import company18.mctank.factory.DiscountFactory;
import company18.mctank.forms.CustomerInfoUpdateForm;
import company18.mctank.forms.LicensePlateForm;
import company18.mctank.forms.SignUpForm;
import company18.mctank.repository.CustomerRepository;
import org.salespointframework.useraccount.*;
import org.salespointframework.useraccount.Password.UnencryptedPassword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;


@Service
public class CustomerService {
	private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);
	private static final long USER_MAXIMUM_INACTIVITY_TIME_IN_MS = 1000 * 60 * 60 * 24 * 120L; // 120 days

	private final CustomerRepository customerRepository;

	private final UserAccountManager userAccountManager;

	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	public CustomerService(CustomerRepository customers, UserAccountManager userAccounts) {

		Assert.notNull(customers, "CustomerRepository must not be null!");
		Assert.notNull(userAccounts, "UserAccountManager must not be null!");

		this.customerRepository = customers;
		this.userAccountManager = userAccounts;
	}


	public void createCustomer(SignUpForm form) throws ExistedUserException {
		Assert.notNull(form, "Registration form must not be null!");
		UnencryptedPassword password = UnencryptedPassword.of(form.getPassword());
		this.createCustomer(form.getLicensePlate(), form.getEmail(), password, CustomerRoles.CUSTOMER);
	}

	public Customer createCustomer(String lic_plate,
								   String email,
								   UnencryptedPassword password,
								   Role role) throws ExistedUserException {
		UserAccount userAccount;
		try {
			userAccount = userAccountManager.create(lic_plate, password, role);
			userAccount.setEmail(email);
		} catch (IllegalArgumentException e) {
			throw new ExistedUserException();
		}
		Customer customer = new Customer(userAccount);
		Discount discount = DiscountFactory.create(DiscountFactory.DiscountType.REGISTRATION);
		customer.addDiscount(discount);
		return customerRepository.save(customer);
	}

	public void disableCustomer(UserAccountIdentifier id) {
		userAccountManager.disable(id);
	}

	public void enableCustomer(UserAccountIdentifier id) {
		userAccountManager.enable(id);
	}

	public void deleteCustomer(long id) {
		Customer customer = customerRepository.findById(id).orElseThrow();
		UserAccount userAccount = customer.getUserAccount();
		customerRepository.delete(customer);
		userAccountManager.delete(userAccount);
	}

	public void updateCustomer(CustomerInfoUpdateForm form) {
		this.updateCustomer(form.getFirstname(),
			form.getLastname(),
			form.getEmail(),
			form.getMobile(),
			form.getId());
	}

	public void updateCustomer(String firstname,
							   String lastname,
							   String email,
							   String mobile,
							   long id) {
		Customer customer = this.getCustomer(id);
		customer.setFirstName(firstname);
		customer.setLastName(lastname);
		customer.setEmail(email);
		customer.setMobile(mobile);
		customerRepository.save(customer);
		LOG.info("Request:  Update User's Info. Done: User " + customer.getUsername() + " was updated");
	}

	public void updateCustomersDiscounts(List<Discount> discounts, long customerId) {
		Customer customer = getCustomer(customerId);
		customer.setDiscounts(discounts);
		customerRepository.save(customer);
	}

	public void updateCurrentCustomerLastActivityDate() {
		Customer customer = this.getCurrentCustomer();
		if (customer != null) {
			customer.updateLastActivityDate();
			this.customerRepository.save(customer);
		}
	}

	public void updatePassword(String newPassword, long id) {
		UserAccount account = this.getUserAccount(id);
		UnencryptedPassword password = Password.UnencryptedPassword.of(newPassword);
		userAccountManager.changePassword(account, password);
		LOG.info("Request: Change password. Status: Completed");
	}

	public List<Customer> findAll() {
		return (List<Customer>) customerRepository.findAll();
	}

	public boolean isAdmin() {
		return this.checkRole(CustomerRoles.ADMIN);
	}

	public boolean isManager() {
		return this.checkRole(CustomerRoles.MANAGER);
	}

	public boolean isCustomer() {
		return this.checkRole(CustomerRoles.CUSTOMER);
	}

	private boolean checkRole(Role role) {
		UserDetails userDetails;
		try {
			userDetails = this.getPrincipal();
			return userDetails.getAuthorities()
				.stream()
				.anyMatch(
					a -> a.getAuthority()
						.contains(role.getName())
				);
		} catch (AnonymusUserException e) {
			return false;
		}
	}

	private UserDetails getPrincipal() throws AnonymusUserException {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal == null) {
			throw new IllegalStateException("Principal can not be null");
		}
		if (!(principal instanceof UserDetails)) {
			throw new AnonymusUserException();
		}
		return (UserDetails) principal;
	}


	public Customer getCurrentCustomer() {
		Customer customer = null;
		try {
			UserAccount userAccount = this.getCurrentUserAccount();
			customer = customerRepository.findCustomerByUserAccount(userAccount);
		} catch (AnonymusUserException e) {
			LOG.warn("Request: Get Current Customer. Failed: User is Anonymous");
		}
		return customer;
	}

	public UserAccount getCurrentUserAccount() throws AnonymusUserException {
		UserDetails userDetails = this.getPrincipal();
		String lic_plate = userDetails.getUsername();
		UserAccount userAccount = this.userAccountManager.findByUsername(lic_plate).orElse(null);
		// TODO: Throw exception of not found user;
		return userAccount;
	}

	public Customer getCustomer(long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (customer.isPresent()) {
			return customer.get();
		} else {
			try {
				throw new UserNotFoundException();
			} catch (UserNotFoundException e) {
				LOG.error(e.getMessage());
				return null;
			}
		}
	}

	public UserAccount getUserAccount(long customerId) {
		return this.getCustomer(customerId).getUserAccount();
	}

	public Customer getCustomer(String lic_plate) {
		UserAccount userAccount = userAccountManager.findByUsername(lic_plate).orElseThrow();
		return customerRepository.findCustomerByUserAccount(userAccount);
	}

	@Transactional
	public void deleteLongInactiveUsers() {
		final Date lastPossibleDate = new Date(System.currentTimeMillis() - USER_MAXIMUM_INACTIVITY_TIME_IN_MS);
		Integer deletedUsers = this.customerRepository.deleteAllByLastActivityDateBefore(lastPossibleDate);
		LOG.info("Users Deleted: {}", deletedUsers);
	}

	public int findAllActiveUsersAmount() {
		return (int) findAll().stream().filter(c -> c.getUserAccount().isEnabled()).count();
	}

	public String findAllActivePercent() {
		float rawPercent = ((float) this.findAllActiveUsersAmount() / this.findAll().size()) * 100f;
		return String.format("%.1f", rawPercent).replace(",", ".");
	}

	public void updateLicensePlate(LicensePlateForm form) {
		// not working since username is license plate
		//Customer customer = getCustomer(form.getId());
		//customer.setLicensePlate(form.getLicensePlate());
	}
}