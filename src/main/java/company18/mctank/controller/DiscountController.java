package company18.mctank.controller;

import company18.mctank.domain.DiscountCart;
import company18.mctank.domain.McTankOrder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.salespointframework.quantity.Quantity;

import java.util.Optional;

import org.salespointframework.catalog.Product;
import org.salespointframework.order.Cart;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;

@Controller
@SessionAttributes("cart")
public class DiscountController {
	private DiscountCart cart;

	DiscountController(DiscountCart cart) {
		this.cart = cart;
	}

	OrderManager<McTankOrder> orderManager;


	void OrderController(OrderManager<McTankOrder> orderManager) {
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
		cart.addOrUpdateItem(product, Quantity.of(amount));   // quantity.of() can't be compared to metric *Liter* --> error --David--
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
	
	@GetMapping(value = "/orders")
	String orders() {

		return "orders";
	}

	/* @PostMapping("/cart/discount")
	public String addDiscount(String discountCode) {
		cart.addDiscount(discountCode);
		return "cart"; */


	@PostMapping("/cart/pay")	
	String buy(@ModelAttribute Cart cart, @LoggedIn Optional<UserAccount> userAccount) {
		
		// creating new order attached to userAccount
		McTankOrder order;
		if (userAccount.isPresent())
			order = new McTankOrder(userAccount.get());
		else return "redirect:/";
		
		// add items to order, pay order, set orders state to completed, clear cart
		cart.addItemsTo(order);
		orderManager.payOrder(order);
		orderManager.completeOrder(order);	
		cart.clear();
		return "redirect:/";
	}
	
	/*
	@GetMapping("/orders")
	@PreAuthorize("hasRole('BOSS')")
	String orders(Model model) {
		model.addAttribute("ordersCompleted", orderManager.findBy(OrderStatus.COMPLETED));
		return "orders";
	}*/
}


