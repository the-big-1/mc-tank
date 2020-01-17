package company18.mctank.controller;

import company18.mctank.exception.ExistedUserException;
import company18.mctank.forms.SignUpForm;
import company18.mctank.service.CustomerService;
import org.salespointframework.useraccount.UserAccountIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * User manager controller.
 *
 * @author ArtemSer
 */
@Controller
@PreAuthorize("hasRole('ADMIN')")
class UserManagementController {

	private static final Logger LOG = LoggerFactory.getLogger(UserManagementController.class);

	@Autowired
	private CustomerService customerService;

	/**
	 * Get customers method.
	 *
	 * @param model model
	 * @return user management view
	 */
	@GetMapping("/user-management")
	String customers(Model model) {
		model.addAttribute("customerList", customerService.findAll());
		return "user-management";
	}

	/**
	 * Registe new user from form.
	 *
	 * @param form form
	 * @return redirect to user management
	 */
	@PostMapping("/customer/new")
	public ResponseEntity<?> registerNew(@RequestBody SignUpForm form) {
		try {
			customerService.createCustomer(form);
		} catch (ExistedUserException e) {
			LOG.error("Request: New user. Failed: Username exists.");
			return ResponseEntity.badRequest().build();
			
		}
		LOG.error("Request: New user. Done: New user was created");
		return ResponseEntity.ok(null);
	}

	/**
	 * Disable customer.
	 *
	 * @param model model
	 * @param id customer id to disable
	 * @return redirect to user management
	 */
	@RequestMapping("/customer/disable")
	String disableCustomer(Model model,  @RequestParam UserAccountIdentifier id){
		customerService.disableCustomer(id);
		model.addAttribute("customerList", customerService.findAll());
		return "redirect:/user-management";
	}

	/**
	 * Enable customer.
	 *
	 * @param model model
	 * @param id customer id to enable
	 * @return redirect to user management
	 */
	@RequestMapping("/customer/enable")
	String enableCustomer(Model model,  @RequestParam UserAccountIdentifier id){
		customerService.enableCustomer(id);
		model.addAttribute("customerList", customerService.findAll());
		return "redirect:/user-management";
	}

	/**
	 * Delete customer.
	 *
	 * @param model model
	 * @param id id customer to delete
	 * @return redirect to user management
	 */
	@RequestMapping("/customer/delete")
	String deleteCustomer(Model model,  @RequestParam long id){
		customerService.deleteCustomer(id);
		model.addAttribute("customerList", customerService.findAll());
		return "redirect:/user-management";
	}

}
