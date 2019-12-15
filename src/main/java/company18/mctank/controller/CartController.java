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

import java.util.List;
import java.util.Optional;

import org.salespointframework.catalog.Product;
import org.salespointframework.quantity.Metric;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;

/**
 * A controller to manage the Cart.
 * @author vivien
 *
 */
@Controller
@SessionAttributes("cart")
public class CartController {
	private McTankCart cart;
	private CartService cartService;
	private GasPumpService pumpService;
	private CustomerService customerService;

	private boolean fuelWarning = false;  // add to model and call warning if true
	
/**
 * 
 * @param cartService
 * @param cart must not be null.
 * @param pumpService
 * @param customerService to handle a Cart session for each user.
 */
	CartController(CartService cartService, @ModelAttribute McTankCart cart, GasPumpService pumpService, CustomerService customerService) {
		Assert.notNull(cart, "Cart must not be null!");
		this.cart = cart;
		Assert.notNull(cartService, "CartService must not be null!");
		this.cartService = cartService;
		this.pumpService = pumpService;
		this.customerService = customerService;
	}
	
	/**
	 * Creates a new cart session to be stored in the session for each user.
	 * @return a new cart instance
	 */
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
	
	/**
	 * 
	 * @param product adds a product to the cart.
	 * @param amount the amount of a product in the cart.
	 * @param claim 
	 * @return the view name.
	 */
	@PostMapping(value = "/cart")
	public String addItem(@RequestParam("product-id") Product product, @RequestParam("amount") int amount, @RequestParam("claim") Optional<Boolean> claim) {
		this.cartService.addOrUpdateItem(this.cart, product, amount, claim.isPresent());
		return "redirect:/cart";
	}

	/**
	 * 
	 * @param username each cart session belongs to a certain user.
	 * @return the view name.
	 */
	@PostMapping("/cart/username")
	public String saveUsername(String username) {
		Customer customer = customerService.getCustomer(username);
		cart.setCustomer(customer);
		cartService.load(cart, customer.getUserAccount());
		return "redirect:/cart";
	}
	
	/**
	 * 
	 * @param product here it is a certain gas pump whose values will be added to the cart.
	 * @param amount amount of litres.
	 * @param number each gas pump has a certain number.
	 * @return the view name.
	 */
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

	/**
	 * Deletes all products in the cart.
	 * @return the cart view.
	 */
	@PostMapping("/cart/clear")
	public String clearCart(){
		this.cart.clear();
		return "redirect:/cart";
	}

	/**
	 * The cart is saved for later use.
	 * @return to cart.
	 */
	@PostMapping("/cart/save")
	public String saveCart(){
		this.cartService.save(cart);
		return "redirect:/cart";
	}
	

	/**
	 * 
	 * @param discountCode each user has a certain discount code.
	 * @return the cart view.
	 */
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


