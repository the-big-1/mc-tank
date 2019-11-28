package company18.mctank.initializer;

import company18.mctank.domain.CustomerRoles;
import company18.mctank.service.CustomerService;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.useraccount.Password.UnencryptedPassword;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

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

	private final UserAccountManager userAccountManager;

	/**
	 * Creates a new {@link CustomerDataInitializer} with the given {@link UserAccountManager} and
	 * {@link CustomerService}.
	 *
	 * @param userAccountManager must not be {@literal null}.
	 * @param сustomerService must not be {@literal null}.
	 */
	CustomerDataInitializer(UserAccountManager userAccountManager, CustomerService сustomerService) {

		Assert.notNull(userAccountManager, "UserAccountManager must not be null!");
		Assert.notNull(сustomerService, "CustomerRepository must not be null!");

		this.userAccountManager = userAccountManager;
	}

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
		userAccountManager.create("boss", password, Role.of(CustomerRoles.ADMIN.getRole()));
		userAccountManager.create("customer", password, Role.of(CustomerRoles.CUSTOMER.getRole()));
		userAccountManager.create("manager", password, Role.of(CustomerRoles.MANAGER.getRole()));

		LOG.info("Created all default user accounts");


	}
}