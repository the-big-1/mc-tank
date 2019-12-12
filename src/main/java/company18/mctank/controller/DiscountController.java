package company18.mctank.controller;

import company18.mctank.domain.DiscountCart;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.salespointframework.quantity.Quantity;

import java.util.List;

import org.salespointframework.catalog.Product;
import org.salespointframework.order.Cart;
import org.salespointframework.order.CartItem;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.order.Order;
import org.salespointframework.payment.Cash;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;


@Controller
@SessionAttributes("cart")
public class DiscountController {
	private DiscountCart cart;

	DiscountController(DiscountCart cart) {
		this.cart = cart;
	}

	OrderManager<Order> orderManager;


	void OrderController(OrderManager<Order> orderManager) {
		Assert.notNull(orderManager, "OrderManager must not be null!");
		this.orderManager = orderManager;
	}


	@ModelAttribute("cart")
	Cart initializeCart() {
		return new Cart();
	}

	@GetMapping(value = "/cart")
	public String showPrice(Model model, @ModelAttribute Cart cart) {
		// price will be shown in cart
		model.addAttribute("finalprice", cart.getPrice());
		return "cart";
	}


	@PostMapping(value = "/cart")
	public String addItem(@RequestParam("product-id") Product product, @RequestParam("amount") int amount, @ModelAttribute Cart cart) {
		cart.addOrUpdateItem(product, product.createQuantity(amount));   // quantity.of() can't be compared to metric *Liter* --> error --David-- -->sollte gefixed sein
		return "redirect:/cart";
	}

	@GetMapping(value = "/checkout")
	public String pay(Model model) {
		//show site for checkout
		//adds attributes for cart
		model.addAttribute("finalprice", cart.getPrice());
		return "checkout";
	}

	@PostMapping("/cart/clear")
	public String clearCart(Model model, @ModelAttribute Cart cart){
		cart.clear();
		return "redirect:/cart";
	}

	@PostMapping("/cart/discount")
	public String addDiscount(String discountCode) {
		cart.addDiscount(discountCode);
		return "cart";

		// for this part a user has to be logged in 
		
	/*String buy(@ModelAttribute Cart cart, @LoggedIn Optional<UserAccount> userAccount) {
		return userAccount.map(account -> {
		
			
		var order = new Order(account, Cash.CASH);

		cart.addItemsTo(order);

		orderManager.payOrder(order);
		orderManager.completeOrder(order);
		 */
	}
	//);
}

