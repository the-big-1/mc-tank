package company18.mctank.controller;

import company18.mctank.domain.DiscountCart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DiscountController {
	private DiscountCart cart;

	DiscountController(DiscountCart cart){
		this.cart = cart;
	}

	@GetMapping(value="/discount")
	public String showPrice(Model model){
		model.addAttribute("price", cart.getPrice().toString());
		return "discount";
	}

	@PostMapping(value="/discount")
	public String addDiscount(@RequestParam("discountCode") String discountCode){
		cart.addDiscount(discountCode);
		return "redirect:/discount";
	}
}
