package company18.mctank.controller;

import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import company18.mctank.domain.McTankOrder;

@Controller
public class OrderHistoryController {
	
	OrderManager<McTankOrder> orderManager;
	
	OrderHistoryController(OrderManager<McTankOrder> orderManager){
		this.orderManager = orderManager;
	}
	
	@GetMapping("/orders")
	String orders(Model model) {
		model.addAttribute("orders", orderManager.findBy(OrderStatus.COMPLETED));
		return "orderhistory";
	}
}
