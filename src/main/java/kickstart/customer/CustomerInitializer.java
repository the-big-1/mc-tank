package kickstart.customer;

import java.util.List;

import org.salespointframework.core.DataInitializer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import kickstart.customer.RegistrationForm;

@Order(10)
@Component
class CustomerInitializer implements DataInitializer {
	
	private final CustomerManagement customerManagement;
	
	CustomerInitializer(CustomerManagement customerManagement) {


		Assert.notNull(customerManagement, "CustomerRepository must not be null!");

		this.customerManagement = customerManagement;
	}
	@Override
	public void initialize() {
		
		var password = "password";

		List.of(//
				new RegistrationForm("Test1", password),
				new RegistrationForm("moar Test", password),
				new RegistrationForm("%&$§_äöp*", password),
				new RegistrationForm("BOB", password)//
		).forEach(customerManagement::createCustomer);
	}
}
