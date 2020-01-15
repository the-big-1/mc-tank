package company18.mctank.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import company18.mctank.service.OrdersService;


@Controller
public class BillController {
	
	@Autowired
	OrdersService ordersService;

	@GetMapping("/bill")
	String bill(Model model) {
		model.addAttribute("bill", ordersService.findAll());
		return "bill";
	}
	
	@PostMapping("/bill")
	public String showBill(@RequestParam @NotEmpty String id){
		ordersService.showOrder(id);
		return "bill";
	}


}


