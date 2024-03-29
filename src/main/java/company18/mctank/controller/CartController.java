package company18.mctank.controller;

import company18.mctank.domain.Customer;

import company18.mctank.domain.CustomerRoles;
import company18.mctank.domain.McTankCart;

import company18.mctank.exception.ExistedUserException;
import company18.mctank.factory.DiscountFactory;
import company18.mctank.service.CartService;
import company18.mctank.service.CustomerService;
import company18.mctank.service.GasPumpService;

import org.salespointframework.payment.*;
import org.salespointframework.useraccount.Password;
import org.salespointframework.useraccount.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.salespointframework.catalog.Product;
import org.salespointframework.quantity.Metric;
import org.salespointframework.quantity.Quantity;

/**
 * A controller to manage the Cart.
 * @author vivien
 * @author ArtemSer
 */
@Controller
@SessionAttributes("cart")
public class CartController {
	private McTankCart cart;
	private CartService cartService;
	private GasPumpService pumpService;
	private CustomerService customerService;

	/**
	 *
	 * Cart controller constructor.
	 *
	 * @param cartService car service
	 * @param cart must not be null.
	 * @param pumpService pump service
	 * @param customerService to handle a Cart session for each user.
	 */
	CartController(CartService cartService, @ModelAttribute McTankCart cart,
				   GasPumpService pumpService, CustomerService customerService) {
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

	/**
	 * Returns view cart.
	 *
	 * @param model model
	 * @return view name
	 */
	@GetMapping(value = "/cart")
	public String showCart(Model model) {
		model.addAttribute("cart", this.cart);
		return "cart";
	}
	
	/**
	 * 
	 * @param product adds a product to the cart.
	 * @param amount the amount of a product in the cart.
	 * @param claim claim
	 * @return the view name.
	 */
	@PostMapping(value = "/cart")
	public String addItem(@RequestParam("product-id") Product product,
						  @RequestParam("amount") int amount,
						  @RequestParam("claim") Optional<Boolean> claim) {
		this.cartService.addOrUpdateItem(this.cart, product, amount, claim.isPresent());
		return "redirect:/cart";
	}

	/**
	 * Adds a user to the Cart if no user with this license plate exists a new Customer is created.
	 *
	 * @param license_plate each cart session belongs to a certain user with this license plate.
	 * @return the view name.
	 */
	@PostMapping("/cart/license_plate")
	public String saveUsername(String license_plate) {

		try {
			Customer customer = customerService.getCustomer(license_plate);
			cart.setCustomer(customer);
			cartService.load(cart, customer.getUserAccount());
		}
		catch (Exception e){
			try {
				//if no customer with this license plate exists -> create new one
				Customer customer_new = customerService.createCustomer(license_plate, null, Password.UnencryptedPassword.of(license_plate), CustomerRoles.CUSTOMER);
				cart.setCustomer(customer_new);
				cartService.load(cart, customer_new.getUserAccount());
			}
			catch (Exception ex){
				return "redirect:/cart";
			}


		}
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
	public String addItem(@RequestParam("product-id") Product product,
						  @RequestParam("amount") float amount,
						  @RequestParam("pump-number") int number) {
		this.cartService.addOrUpdateItem(this.cart, product, Quantity.of(amount, Metric.LITER));
		return "redirect:/cart";
	}

	/**
	 * Add item by pump number.
	 *
	 * @param number pump number
	 * @return view name
	 */
	@PostMapping(value = "/cart/pump/direct")
	public String addItem(@RequestParam("pump-number") int number) {
		if (this.pumpService.isInValid(number)) {
			return "redirect:/";
		} else {
			this.cartService.addOrUpdateItem(this.cart,
											 pumpService.getFuel(number),
											 Quantity.of(pumpService.getFuelQuantity(number), Metric.LITER));
		}
		this.pumpService.pumpsInit();
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

	/**
	 * Checkout mapping.
	 *
	 * @return response entity
	 */
	@PostMapping("/cart/checkout")
	public ResponseEntity<?> checkout() {
		if (this.cartService.buy(this.cart, Cash.CASH)) {
			this.handleDiscount();
			this.cart.clear();
			customerService.getCurrentCustomer().setLastOrderDate(LocalDateTime.now());
			return ResponseEntity
					 .ok()
					 .build();
		} else {
			return ResponseEntity
					.status(HttpStatus.NOT_IMPLEMENTED)
					.build();
		}
	}

	/**
	 * Handle discount.
	 */
	private void handleDiscount() {
		this.removeDiscounts();
		this.addDiscounts();
		this.updateDiscounts();
	}

	/**
	 * Remove discount from customer in cart.
	 */
	private void removeDiscounts () {
		this.cart.get().forEach(cartItem -> {
			if (cartItem.getPrice().isNegative()) {
				cart.getCustomer().removeDiscount(cartItem.getProductName());
			}
		});
	}

	/**
	 * Add discount to customer in cart.
	 */
	private void addDiscounts () {
		this.cart.getCustomer()
				.addDiscount(DiscountFactory.create(this.cart.getMcPointBonus()));
	}

	/**
	 * Update discount in repository.
	 */
	private void updateDiscounts () {
		this.customerService.updateCustomersDiscounts(
				this.cart.getCustomer().getDiscounts(),
				this.cart.getCustomer().getId()
		);
	}
}


