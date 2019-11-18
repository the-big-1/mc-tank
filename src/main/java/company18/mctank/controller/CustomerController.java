package company18.mctank.controller;

import javax.validation.Valid;

import company18.mctank.forms.RegistrationForm;
import company18.mctank.service.CustomerService;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
class CustomerController {
	public static Role CUSTOMER_ROLE = Role.of("CUSTOMER");

	private final CustomerService customerService;

	CustomerController(CustomerService customerService) {

		Assert.notNull(customerService, "CustomerManagement must not be null!");

		this.customerService = customerService;
	}

	@PostMapping("/sign-up")
	String registerNew(@Valid RegistrationForm form, Errors result) {

		if (result.hasErrors()) {
			return "sign-up";
		}

		customerService.createCustomer(form);

		return "redirect:/customer-list";
	}
	@RequestMapping("/sign-up")
	String registrierung() {
		return "sign-up";
	}

	@GetMapping("/customer-list")
	String customers(Model model) {
		model.addAttribute("customerList", customerService.findAll());
		return "customer-list";
	}

	@RequestMapping("/customer/disable")
	String disableCustomer(Model model,  @RequestParam UserAccountIdentifier id){
		customerService.disableCustomer(id);
		model.addAttribute("customerList", customerService.findAll());
		return "redirect:/customer-list";
	}
	@RequestMapping("/customer/enable")
	String enableCustomer(Model model,  @RequestParam UserAccountIdentifier id){
		customerService.enableCustomer(id);
		model.addAttribute("customerList", customerService.findAll());
		return "redirect:/customer-list";
	}

	@RequestMapping("/customer/delete")
	String deleteCustomer(Model model,  @RequestParam long id){
		customerService.deleteCustomer(id);
		model.addAttribute("customerList", customerService.findAll());
		return "redirect:/customer-list";
	}

}
