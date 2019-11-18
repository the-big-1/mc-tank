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

		List.of(
				new RegistrationForm("Edward Snowden", password),
				new RegistrationForm("Linus Torvalds", password),
				new RegistrationForm("John Doe", password),
				new RegistrationForm("Hue Lori", password),
				new RegistrationForm("Tony Stark", password)
		).forEach(customerManagement::createCustomer);
	}
}
