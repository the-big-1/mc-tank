package company18.mctank.controller;

import company18.mctank.service.CustomerService;
import org.salespointframework.useraccount.UserAccountIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PreAuthorize("hasRole('ADMIN')")
class UserManagementController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/user-management")
	String customers(Model model) {
		model.addAttribute("customerList", customerService.findAll());
		return "user-management";
	}

	@RequestMapping("/customer/disable")
	String disableCustomer(Model model,  @RequestParam UserAccountIdentifier id){
		customerService.disableCustomer(id);
		model.addAttribute("customerList", customerService.findAll());
		return "redirect:/user-management";
	}
	@RequestMapping("/customer/enable")
	String enableCustomer(Model model,  @RequestParam UserAccountIdentifier id){
		customerService.enableCustomer(id);
		model.addAttribute("customerList", customerService.findAll());
		return "redirect:/user-management";
	}

	@RequestMapping("/customer/delete")
	String deleteCustomer(Model model,  @RequestParam long id){
		customerService.deleteCustomer(id);
		model.addAttribute("customerList", customerService.findAll());
		return "redirect:/user-management";
	}

}
