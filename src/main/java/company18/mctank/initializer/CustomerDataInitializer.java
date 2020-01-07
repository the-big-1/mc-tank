package company18.mctank.initializer;

import company18.mctank.domain.CustomerRoles;
import company18.mctank.exception.ExistedUserException;
import company18.mctank.repository.CustomerRepository;
import company18.mctank.service.CustomerService;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.useraccount.Password.UnencryptedPassword;
import org.salespointframework.useraccount.UserAccountManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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

		// dont initialize if already populated
		if (userAccountManager.findByUsername("boss").isPresent()) {
			return;
		}

		LOG.info("Initializing: users and customer");

		UnencryptedPassword password = UnencryptedPassword.of("123");
		try {
			List.of(
				customerService.createCustomer("boss", "test1@mctank.com", UnencryptedPassword.of("123"),  CustomerRoles.ADMIN),
				customerService.createCustomer("test", "test2@mctank.com", UnencryptedPassword.of("test"),  CustomerRoles.CUSTOMER),
				customerService.createCustomer("manager", "test3@mctank.com", UnencryptedPassword.of("123"), CustomerRoles.MANAGER)
			).forEach(customerRepository::save);
		} catch (ExistedUserException e) {
			LOG.error("Initializing : users and customer. Failed: User exist");
		}

		LOG.info("Initializing: users and customer. Done.");


	}
}