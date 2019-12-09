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
import org.salespointframework.payment.Cash;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;

@Controller
@SessionAttributes("cart")
public class DiscountController {
	private DiscountCart cart;
	OrderManager<McTankOrder> orderManager;

	DiscountController(DiscountCart cart, OrderManager<McTankOrder> orderManager) {
		this.cart = cart;
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

	@PostMapping("/cart/clear")
	public String clearCart(Model model, @ModelAttribute Cart cart){
		cart.clear();
		return "redirect:/cart";
	}
	

	@PostMapping("/cart/discount")
	public String addDiscount(String discountCode) {
			cart.addDiscount(discountCode);
		return "redirect:/cart";
	}	


	@PostMapping("/cart/pay")	
	String buy(@ModelAttribute Cart cart, @LoggedIn Optional<UserAccount> userAccount) {
		
		// creating new order attached to userAccount
		McTankOrder order;
		if (userAccount.isPresent())
			order = new McTankOrder(userAccount.get());
		else return "redirect:/";
		
		// add items to order
	    cart.addItemsTo(order);
		
		// set paymentmethod to cash and pay
		order.setPaymentMethod(Cash.CASH);
		orderManager.payOrder(order);
		
		// set order state to completed
		orderManager.completeOrder(order);	
		
		//save order
		orderManager.save(order);
		
		// clear cart and redirect
		cart.clear();
		return "redirect:/";
	}
}


