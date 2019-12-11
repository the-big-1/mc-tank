package company18.mctank.controller;

import java.util.Collections;
import java.util.List;

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
		List<McTankOrder> orders = this.orderManager.findBy(OrderStatus.COMPLETED).toList();
		Collections.reverse(orders);
		model.addAttribute("orders", orders);
		return "orderhistory";
	}
}
