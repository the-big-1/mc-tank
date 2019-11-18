package kickstart.customer;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManager;
import org.salespointframework.useraccount.Password.UnencryptedPassword;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import kickstart.customer.Customer;
import kickstart.customer.RegistrationForm;

@Repository
public class CustomerManagement {

	public static Role CUSTOMER_ROLE = Role.of("CUSTOMER");
	private final CustomerRepository customers;
	private final UserAccountManager userAccounts;

	CustomerManagement(CustomerRepository customers, UserAccountManager userAccounts) {

		Assert.notNull(customers, "CustomerRepository must not be null!");
		Assert.notNull(userAccounts, "UserAccountManager must not be null!");

		this.customers= customers;
		this.userAccounts = userAccounts;
	}

	public Customer createCustomer(RegistrationForm form) {

		Assert.notNull(form, "Registration form must not be null!");

		var password = UnencryptedPassword.of(form.getPassword());
		var userAccount = userAccounts.create(form.getName(), password, CUSTOMER_ROLE);

		return customers.save(new Customer(userAccount));
	}

	public Iterable<Customer> findAll() {
		return customers.findAll();
	}
}