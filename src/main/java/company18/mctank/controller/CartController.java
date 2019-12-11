package company18.mctank.controller;

import company18.mctank.domain.McTankCart;

import company18.mctank.service.CartService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Optional;

import org.salespointframework.catalog.Product;
import org.salespointframework.payment.Cash;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;

@Controller
@SessionAttributes("cart")
	public class CartController {
	private McTankCart cart;
	private CartService cartService;
	
	
	CartController(CartService cartService, @ModelAttribute McTankCart cart) {
		Assert.notNull(cart, "Cart must not be null!");
		this.cart = cart;
		Assert.notNull(cartService, "CartService must not be null!");
		this.cartService = cartService;
	}
	
	@ModelAttribute("cart")
	public McTankCart initialize() {
		return new McTankCart();
	}

	@GetMapping(value = "/cart")
	public String showCart(Model model) {
		model.addAttribute("cart", this.cart);
		return "cart";
	}


	@PostMapping(value = "/cart")
	public String addItem(@RequestParam("product-id") Product product, @RequestParam("amount") int amount, @RequestParam("claim") Optional<Boolean> claim) {
		this.cartService.addOrUpdateItem(this.cart ,product, amount, claim.isPresent());
		cart.McPointBonus();
		return "redirect:/cart";
	}

	@PostMapping("/cart/clear")
	public String clearCart(){
		this.cart.clear();
		return "redirect:/cart";
	}
	

	@PostMapping("/cart/discount")
	public String addDiscount(String discountCode) {
		cart.addDiscount(discountCode);
		return "redirect:/cart";
	}	


	@PostMapping("/cart/pay")	
	String buy(@LoggedIn Optional<UserAccount> userAccount) {
		 if (this.cartService.buy(this.cart, userAccount, Cash.CASH))  // paymentMethod set to cash for now
			 return "redirect:/";			// successful
		 else return "redirect:/cart";		// or not
	}
}


