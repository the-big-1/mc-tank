package company18.mctank.controller;

import java.util.stream.Stream;

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
	/**
	 * Adds all completed {@link McTankOrder}s to model.
	 * @param model model
	 * @return templates name
	 */
	@GetMapping("/orders")
	String orders(Model model) {
		Stream<McTankOrder> orders =  this.orderManager.findBy(OrderStatus.COMPLETED).get().sorted();
		model.addAttribute("orders", (Iterable<McTankOrder>) orders::iterator);
		return "orderhistory";
	}
}
