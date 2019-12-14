package company18.mctank.controller;

import company18.mctank.forms.CustomerInfoUpdateForm;
import company18.mctank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Controller
public class AccountController {

	@Autowired
	CustomerService customerService;

	@GetMapping("/account")
	public String index(Model model) {
		model.addAttribute("customer", customerService.getCurrentCustomer());
		return "user-account";
	}

	@PostMapping("/account/update")
	public String updateInfo(@Valid CustomerInfoUpdateForm form){
		customerService.updateCustomer(form);
		return "redirect:/account";
	}

	@PostMapping("/account/new-password")
	public String updatePassword(@RequestParam @NotEmpty String password, @RequestParam @NotEmpty long id){
		customerService.updatePassword(password, id);
		return "redirect:/account";
	}

	@PostMapping("/account/delete")
	public String deleteAccount(@RequestParam @NotEmpty long id){
		customerService.deleteCustomer(id);
		return "redirect:/logout";
	}
}
