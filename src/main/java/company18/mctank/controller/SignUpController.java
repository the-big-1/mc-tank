package company18.mctank.controller;

import company18.mctank.forms.RegistrationForm;
import company18.mctank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class SignUpController {

	@Autowired
	private CustomerService customerService;

	@PostMapping("/sign-up")
	String registerNew(@Valid RegistrationForm form, Errors result) {

		if (result.hasErrors()) {
			return "sign-up";
		}
		customerService.createCustomer(form);
		return "redirect:/";
	}

	@GetMapping("/sign-up")
	String registrierung() {
		return "sign-up";
	}
}
