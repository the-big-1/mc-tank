package company18.mctank.service;

import company18.mctank.domain.Customer;
import company18.mctank.domain.CustomerRoles;
import company18.mctank.domain.Discount;
import company18.mctank.exception.ExistedUserException;
import company18.mctank.exception.AnonymusUserException;
import company18.mctank.exception.UserNotFoundException;
import company18.mctank.factory.DiscountFactory;
import company18.mctank.forms.CustomerInfoUpdateForm;
import company18.mctank.forms.SignUpForm;
import company18.mctank.domain.McTankOrder;
import company18.mctank.exception.UnauthorizedUserException;
import company18.mctank.forms.RegistrationForm;

import company18.mctank.repository.CustomerRepository;

import org.salespointframework.useraccount.*;


import org.salespointframework.order.OrderManager;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountIdentifier;
import org.salespointframework.useraccount.UserAccountManager;

import org.salespointframework.useraccount.Password.UnencryptedPassword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


import java.util.List;

import static java.util.concurrent.TimeUnit.MINUTES;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;


@Service
public class CustomerService {
	private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);


	private final CustomerRepository customerRepository;

	private final UserAccountManager userAccountManager;

	private static Role CUSTOMER_ROLE = Role.of("CUSTOMER");
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private final OrderManager<McTankOrder> orderManager;
	private LocalDateTime newYear = LocalDateTime.parse("2020-1-1T0:0:0");
	private long initialDelay = LocalDateTime.now().until(newYear,ChronoUnit.MINUTES);
	private final CustomerRepository customers;

	private final UserAccountManager userAccounts;


	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	public CustomerService(CustomerRepository customers, UserAccountManager userAccounts,OrderManager<McTankOrder> orderManager) {

		Assert.notNull(customers, "CustomerRepository must not be null!");
		Assert.notNull(userAccounts, "UserAccountManager must not be null!");


		this.customerRepository = customers;
		this.userAccountManager = userAccounts;

		this.customers = customers;
		this.userAccounts = userAccounts;
		this.orderManager = orderManager;

	}


	public Customer createCustomer(SignUpForm form) throws ExistedUserException {
		Assert.notNull(form, "Registration form must not be null!");
		UnencryptedPassword password = UnencryptedPassword.of(form.getPassword());
		return this.createCustomer(form.getName(), form.getEmail(), password, CustomerRoles.CUSTOMER);
	}

	public Customer createCustomer(String username,
								   String email,
								   UnencryptedPassword password,
								   Role role) throws ExistedUserException {
		UserAccount userAccount = null;
		try {
			userAccount = userAccountManager.create(username, password, role);
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
		LOG.info("Request:  Update User's Info. Done: User " + customer.getUsername() +" was updated");
	}

	public void updateCustomersDiscounts(List<Discount> discounts, long customerId) {
		Customer customer = getCustomer(customerId);
		customer.setDiscounts(discounts);
		customerRepository.save(customer);
	}

	public void updatePassword(String newPassword, long id) {
		UserAccount account = this.getUserAccount(id);
		UnencryptedPassword password = Password.UnencryptedPassword.of(newPassword);
		userAccountManager.changePassword(account, password);
		LOG.info("Request: Change password. Status: Completed");
	}

	public Iterable<Customer> findAll() {
		return customerRepository.findAll();
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
		UserDetails userDetails = null;
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
		String username = userDetails.getUsername();
		UserAccount userAccount = this.userAccountManager.findByUsername(username).orElse(null);
		// TODO: Throw exception of not found user;
		return userAccount;
	}

	public Customer getCustomer(long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (customer.isPresent()) {
			return customer.get();
		} else try {
			throw new UserNotFoundException();
		} catch (UserNotFoundException e) {
			LOG.error(e.getMessage());
			return null;
		}
	}

	public UserAccount getUserAccount(long customerId) {
		return this.getCustomer(customerId).getUserAccount();
	}

	public Customer getCustomer(String username) {
		UserAccount userAccount = userAccountManager.findByUsername(username).orElseThrow();
		return customerRepository.findCustomerByUserAccount(userAccount);
	}

	public void springCleaning() {
		//TODO updating lastOrderDate when creating new order
		final Runnable cleaning = new Runnable() { 
			public void run() {
				for(McTankOrder s:orderManager.findAll(PageRequest.of(0,10))) {
					if(s.getDateCreated().until(newYear,ChronoUnit.DAYS)>=100) {
							orderManager.delete(s);
						}
					}
				for(Customer c:customers.findAll()) {
						if(c.getLastOrderDate().until(newYear, ChronoUnit.DAYS)>=365) {
							customers.delete(c);	
						}
					}
				newYear.plusYears(1);
				}
			};
			final ScheduledFuture<?> cleaningHandle = scheduler.scheduleAtFixedRate(cleaning,initialDelay, 525600,MINUTES);
		};


}