package company18.mctank.controller;

import company18.mctank.domain.Customer;
import company18.mctank.domain.Discount;
import company18.mctank.domain.McTankCart;

import company18.mctank.factory.DiscountFactory;
import company18.mctank.service.CartService;
import company18.mctank.service.CustomerService;
import company18.mctank.service.GasPumpService;

import org.salespointframework.payment.*;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
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
	private CustomerService customerService;

	CartController(CartService cartService, @ModelAttribute McTankCart cart, GasPumpService pumpService, CustomerService customerService) {
		Assert.notNull(cart, "Cart must not be null!");
		this.cart = cart;
		Assert.notNull(cartService, "CartService must not be null!");
		this.cartService = cartService;
		this.pumpService = pumpService;
		this.customerService = customerService;
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
		this.cartService.addOrUpdateItem(this.cart, product, amount, claim.isPresent());
		return "redirect:/cart";
	}

	@PostMapping("/cart/username")
	public String saveUsername(String username) {
		Customer customer = customerService.getCustomer(username);
		cart.setCustomer(customer);
		cartService.load(cart, customer.getUserAccount());
		return "redirect:/cart";
	}
	
	@PostMapping(value = "/cart/pump")
	public String addItem(@RequestParam("product-id") Product product, @RequestParam("amount") float amount, @RequestParam("pump-number") int number) {
		this.cartService.addOrUpdateItem(this.cart, product, Quantity.of(amount, Metric.LITER));
		return "redirect:/cart";
	}
	
	@PostMapping(value = "/cart/pump/direct")
	public String addItem(@RequestParam("pump-number") int number) {
		if (this.pumpService.isInValid(number))
			return "redirect:/";
		else
			this.cartService.addOrUpdateItem(this.cart, pumpService.getFuel(number), Quantity.of(pumpService.getFuelQuantity(number), Metric.LITER));
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
	public ResponseEntity<?> checkout() {
		if (this.cartService.buy(this.cart, Cash.CASH)) {
			this.handleDiscount();
			this.cart.clear();
			customerService.getCurrentCustomer().setLastOrderDate(LocalDateTime.now());
			return ResponseEntity
					 .ok()
					 .build();
		}
		else
			return ResponseEntity
					.status(HttpStatus.NOT_IMPLEMENTED)
					.build();
	}

	private void handleDiscount() {
		this.removeDiscounts();
		this.addDiscounts();
		this.updateDiscounts();
	}

	private void removeDiscounts () {
		this.cart.get().forEach(cartItem -> {
			if (cartItem.getPrice().isNegative()) {
				cart.getCustomer().removeDiscount(cartItem.getProductName());
			}
		});
	}

	private void addDiscounts () {
		this.cart.getCustomer()
				.addDiscount(DiscountFactory.create(this.cart.getMcPointBonus()));
	}

	private void updateDiscounts () {
		this.customerService.updateCustomersDiscounts(
				this.cart.getCustomer().getDiscounts(),
				this.cart.getCustomer().getId()
		);
	}

}


