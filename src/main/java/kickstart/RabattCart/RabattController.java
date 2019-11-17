package kickstart.RabattCart;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RabattController {
	private RabattCart cart;

	RabattController(RabattCart cart){
		this.cart = cart;
	}

	@GetMapping(value="/rabatt")
	public String showPrice(Model model) {
		model.addAttribute("preis", cart.getPrice().toString());
		return "rabatt";
	}

	@PostMapping(value="/rabatt")
	public String addDiscount(@RequestParam("rabattCode") String rabattCode) {
		cart.addDiscount(rabattCode);
		return "redirect:/rabatt";
	}
}
