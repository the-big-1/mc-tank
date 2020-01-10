package company18.mctank.controller;

import company18.mctank.forms.DataStacked;
import company18.mctank.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import company18.mctank.domain.McTankOrder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotEmpty;

/**
 * Order history controller.
 * Add orders to model.
 */
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

	@PostMapping("/order/delete")
	public String deleteOrder(@RequestParam @NotEmpty String id){
		ordersService.deleteOrderBy(id);
		return "redirect:/";
	}

	@GetMapping("/api/orders/stacked")
	@ResponseBody
	public DataStacked getStackedData(){
		DataStacked data = ordersService.stackData();
		return data;
	}
}
