package company18.mctank.service;

import java.util.Optional;


import org.salespointframework.catalog.Product;
import org.salespointframework.order.OrderManager;
import org.salespointframework.payment.PaymentMethod;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.stereotype.Service;

import company18.mctank.domain.McTankCart;
import company18.mctank.domain.McTankOrder;

@Service
public class CartService {
	private OrderManager<McTankOrder> orderManager;
	
	public CartService(OrderManager<McTankOrder> orderManager) {
		this.orderManager = orderManager;
	}
	
	public boolean buy(McTankCart cart, Optional<UserAccount> userAccount, PaymentMethod payMethod) {
		McTankOrder order;
		// check for userAccount
		if (userAccount.isPresent())
			//creating new Order attached to account
			order = new McTankOrder(userAccount.get());
		else return false;
		
		// add items to order
	    cart.addItemsTo(order);
		
		// set paymentmethod
		order.setPaymentMethod(payMethod);
		this.orderManager.payOrder(order);
		
		// set order state to completed
		this.orderManager.completeOrder(order);
		
		//save order
		this.orderManager.save(order);
		
		// clear cart and redirect
		cart.clear();
		return true;
	}
	
	public void addOrUpdateItem(McTankCart cart, Product product, int amount, boolean claim) {
		if (claim) {
			Product negatedProduct = new Product(product.getName().concat(" REKLAMATION"), product.getPrice().negate());
			cart.addOrUpdateItem(negatedProduct, amount);
		} else cart.addOrUpdateItem(product, amount);
	}
	
	public void addOrUpdateItem(McTankCart cart, Product product, Quantity amount) {
		 cart.addOrUpdateItem(product, amount);
	}
}
