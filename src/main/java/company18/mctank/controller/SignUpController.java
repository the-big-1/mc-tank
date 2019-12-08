package company18.mctank.controller;

import company18.mctank.exception.ExistedUserException;
import company18.mctank.forms.RegistrationForm;
import company18.mctank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class SignUpController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/sign-up", method = RequestMethod.POST)
	String registerNew(@Valid RegistrationForm form, Errors result) {
		if (result.hasErrors()) {
			return "sign-up";
		}
		try {
			customerService.createCustomer(form);
		} catch (ExistedUserException e) {
			return "redirect:/sign-up?error";
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/sign-up", method = RequestMethod.GET)
	String registrierung() {
		return "sign-up";
	}
}
