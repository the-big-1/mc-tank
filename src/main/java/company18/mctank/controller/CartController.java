package company18.mctank.controller;

import company18.mctank.domain.McTankCart;

import company18.mctank.service.CartService;
import company18.mctank.service.GasPumpService;

import org.salespointframework.payment.*;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import org.salespointframework.catalog.Product;
import org.salespointframework.quantity.Metric;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;

@Controller
@SessionAttributes("cart")
public class CartController {
	private McTankCart cart;
	private CartService cartService;
	private GasPumpService pumpService;
	private UserAccountManager userAccountManager;
	private boolean fuelWarning = false;

	CartController(CartService cartService, @ModelAttribute McTankCart cart, GasPumpService pumpService, UserAccountManager userAccountManager) {
		Assert.notNull(cart, "Cart must not be null!");
		this.cart = cart;
		Assert.notNull(cartService, "CartService must not be null!");
		this.cartService = cartService;
		this.pumpService = pumpService;
		this.userAccountManager = userAccountManager;
	}
	
	@ModelAttribute("cart")
	public McTankCart initialize() {
		return new McTankCart();
	}

	@GetMapping(value = "/cart")
	public String showCart(Model model) {
		model.addAttribute("cart", this.cart);
		model.addAttribute("warning", fuelWarning); //idea: if true display warning
		return "cart";
	}

	@PostMapping(value = "/cart")
	public String addItem(@RequestParam("product-id") Product product, @RequestParam("amount") int amount, @RequestParam("claim") Optional<Boolean> claim) {
		this.cartService.addOrUpdateItem(this.cart, product, amount, claim.isPresent());
		this.cart.mcPointBonus();
		return "redirect:/cart";
	}

	@PostMapping("/cart/username")
	public String saveUsername(String username) {
		UserAccount userAccount = userAccountManager.findByUsername(username).orElseThrow();
		cart.setOwner(userAccount);
		cartService.load(cart, userAccount);
		return "redirect:/cart";
	}
	
	@PostMapping(value = "/cart/pump")
	public String addItem(@RequestParam("product-id") Product product, @RequestParam("amount") float amount, @RequestParam("pump-number") int number) {
		this.cartService.addOrUpdateItem(this.cart, product, Quantity.of(amount, Metric.LITER));
		this.cart.mcPointBonus();
		return "redirect:/cart";
	}
	
	@PostMapping(value = "/cart/pump/direct")
	public String addItem(@RequestParam("pump-number") int number) {
		if (this.pumpService.isInValid(number))
			return "redirect:/";
		else
			this.cartService.addOrUpdateItem(this.cart, pumpService.getFuel(number), Quantity.of(pumpService.getFuelQuantity(number), Metric.LITER));
		this.cart.mcPointBonus();
		return "redirect:/cart";
	}

	@PostMapping("/cart/clear")
	public String clearCart(){
		this.cart.clear();
		return "redirect:/cart";
	}

	@PostMapping("/cart/save")
	public String saveCart(){
		this.cartService.save(cart);
		return "redirect:/cart";
	}
	

	@PostMapping("/cart/discount")
	public String addDiscount(String discountCode) {
		this.cart.addDiscount(discountCode);
		return "redirect:/cart";
	}	


	@PostMapping("/cart/checkout")
	public ResponseEntity<?> checkout(@LoggedIn Optional<UserAccount> userAccount) {
		 if (this.cartService.buy(this.cart, userAccount, Cash.CASH)) {
			 return ResponseEntity
					 .ok()
					 .build();
		 }
		 else
		 	return ResponseEntity
					.status(HttpStatus.NOT_IMPLEMENTED)
					.build();
	}

	public boolean getFuelWarning(){
		return fuelWarning;
	}

	public void setFuelWarning(boolean fuelWarning) {
		this.fuelWarning = fuelWarning;
	}
}


