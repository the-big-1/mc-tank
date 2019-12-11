package company18.mctank.service;

import java.util.Optional;

import org.salespointframework.order.OrderManager;
import org.salespointframework.payment.PaymentMethod;
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
		orderManager.payOrder(order);
		
		// set order state to completed
		orderManager.completeOrder(order);	
		
		//save order
		orderManager.save(order);
		
		// clear cart and redirect
		cart.clear();
		return true;
	}
}
