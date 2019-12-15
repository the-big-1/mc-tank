package company18.mctank.controller;

import company18.mctank.service.OrdersService;
import org.salespointframework.order.OrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import company18.mctank.domain.McTankOrder;

@Controller
public class OrderHistoryController {
	
	@Autowired
	OrdersService ordersService;

	/**
	 * Adds all completed {@link McTankOrder}s to model.
	 * @param model model
	 * @return templates name
	 */
	@GetMapping("/orders")
	String orders(Model model) {
		model.addAttribute("orders", ordersService.findAll());
		return "orderhistory";
	}
}
