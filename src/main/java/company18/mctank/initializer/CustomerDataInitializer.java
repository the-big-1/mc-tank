package company18.mctank.initializer;

import company18.mctank.domain.Customer;

import company18.mctank.domain.CustomerRoles;
import company18.mctank.forms.RegistrationForm;
import company18.mctank.repository.CustomerRepository;
import company18.mctank.service.CustomerService;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.useraccount.Password.UnencryptedPassword;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Initializes default user accounts and customers. The following are created:
 * <ul>
 * <li>An admin user named "boss".</li>
 * <li>The customers "hans", "dextermorgan", "earlhickey", "mclovinfogell" backed by user accounts with the same
 * name.</li>
 * </ul>
 *
 * @author Oliver Gierke
 */
@Component
@Order(10)
class CustomerDataInitializer implements DataInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(CustomerDataInitializer.class);

	@Autowired
	private UserAccountManager userAccountManager;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerRepository customerRepository;


	/*
	 * (non-Javadoc)
	 * @see org.salespointframework.core.DataInitializer#initialize()
	 */
	@Override
	public void initialize() {

		// Skip creation if database was already populated
		if (userAccountManager.findByUsername("boss").isPresent()) {
			return;
		}

		LOG.info("Creating default users and customers.");

		UnencryptedPassword password = UnencryptedPassword.of("123");
		List.of(
			customerService.createCustomer("boss", UnencryptedPassword.of("123"), Role.of(CustomerRoles.ADMIN.name())),
			customerService.createCustomer("test", UnencryptedPassword.of("test"), Role.of(CustomerRoles.CUSTOMER.name())),
			customerService.createCustomer("manager", UnencryptedPassword.of("123"), Role.of(CustomerRoles.MANAGER.name()))

		).forEach(customerRepository::save);

		LOG.info("Created all default user accounts");


	}
}