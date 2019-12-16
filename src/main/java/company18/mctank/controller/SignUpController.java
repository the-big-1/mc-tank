package company18.mctank.controller;

import company18.mctank.exception.ExistedUserException;
import company18.mctank.forms.SignUpForm;
import company18.mctank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

/**
 * Sign up controller.
 */
@Controller
public class SignUpController {

	@Autowired
	private CustomerService customerService;

	/**
	 * Register new user
	 * @param form form with data
	 * @param result errors if found some
	 * @return redirect to view with error of main view
	 */
	@PostMapping("/sign-up")
	String registerNew(@Valid SignUpForm form, Errors result) {
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

	/**
	 * Returns sin-up page.
	 *
	 * @return sign-up view
	 */
	@GetMapping("/sign-up")
	String registrierung() {
		return "sign-up";
	}
}
