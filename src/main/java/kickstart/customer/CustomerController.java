package kickstart.customer;


import javax.validation.Valid;

import org.salespointframework.useraccount.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import kickstart.customer.CustomerManagement;
import kickstart.customer.RegistrationForm;

@Controller
class CustomerController {
	public static Role CUSTOMER_ROLE = Role.of("CUSTOMER");

	private final CustomerManagement customerManagement;
	
	CustomerController(CustomerManagement customerManagement) {

		Assert.notNull(customerManagement, "CustomerManagement must not be null!");

		this.customerManagement = customerManagement;
	}	
	
	@PostMapping("/registrierung")
	String registerNew(@Valid RegistrationForm form, Errors result) {

		if (result.hasErrors()) {
			return "registrierung";
		}
		
		customerManagement.createCustomer(form);

		return "redirect:/customerList";
	}
	@RequestMapping("/registrierung")
	String registrierung() {		
		return "registrierung";
	}
	
	@GetMapping("/customerList")
	String customers(Model model) {

		model.addAttribute("customerList", customerManagement.findAll());

		return "customerList";
	}

}

