package company18.mctank.controller;

import company18.mctank.domain.McTankOrder;
import org.salespointframework.order.OrderIdentifier;
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


	@PostMapping("/bill")
	public String showBill(Model model, @RequestParam String id){
		McTankOrder order = ordersService.findOrderById(ordersService.findAll(), id);
		model.addAttribute("order", order);

		return "bill";
	}


}


