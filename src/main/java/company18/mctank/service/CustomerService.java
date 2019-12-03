package company18.mctank.service;

import company18.mctank.domain.Customer;
import company18.mctank.domain.CustomerRoles;
import company18.mctank.exception.UnauthorizedUserException;
import company18.mctank.forms.RegistrationForm;
import company18.mctank.repository.CustomerRepository;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountIdentifier;
import org.salespointframework.useraccount.UserAccountManager;
import org.salespointframework.useraccount.Password.UnencryptedPassword;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class CustomerService {

	private static Role CUSTOMER_ROLE = Role.of("CUSTOMER");

	private final CustomerRepository customers;

	private final UserAccountManager userAccounts;

	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	public CustomerService(CustomerRepository customers, UserAccountManager userAccounts) {

		Assert.notNull(customers, "CustomerRepository must not be null!");
		Assert.notNull(userAccounts, "UserAccountManager must not be null!");

		this.customers = customers;
		this.userAccounts = userAccounts;
	}


	public Customer createCustomer(RegistrationForm form) {

		Assert.notNull(form, "Registration form must not be null!");

		var password = UnencryptedPassword.of(form.getPassword());
		var userAccount = userAccounts.create(form.getName(), password, CUSTOMER_ROLE);
		return customers.save(new Customer(userAccount));
	}

	public Customer createCustomer(String username, UnencryptedPassword password, Role role) {
		var userAccount = userAccounts.create(username, password, role);
		return customers.save(new Customer(userAccount));
	}

	public void disableCustomer(UserAccountIdentifier id) {
		userAccounts.disable(id);
	}

	public void enableCustomer(UserAccountIdentifier id) {
		userAccounts.enable(id);
	}

	public void deleteCustomer(long id) {
		Customer customer = customers.findById(id).orElseThrow();
		UserAccount userAccount = customer.getUserAccount();
		customers.delete(customer);
		userAccounts.delete(userAccount);
	}

	public Iterable<Customer> findAll() {
		return customers.findAll();
	}

	public UserDetails getPrincipal() throws UnauthorizedUserException {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal == null){
			throw new IllegalStateException("Principal can not be null");
		}
		if (!(principal instanceof UserDetails)){
			throw new UnauthorizedUserException();
		}
		return (UserDetails) principal;
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

	private boolean checkRole(CustomerRoles role) {
		UserDetails userDetails = null;
		try {
			userDetails = this.getPrincipal();
			return userDetails.getAuthorities()
				.stream()
				.anyMatch(
					a -> a.getAuthority()
						.equals(role.getRole())
				);
		} catch (UnauthorizedUserException e) {
			return false;
		}
	}


}