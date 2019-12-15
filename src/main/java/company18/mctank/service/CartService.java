package company18.mctank.service;

import java.util.Optional;
import java.util.function.Predicate;

import org.salespointframework.catalog.Product;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.PaymentMethod;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.stereotype.Service;

import company18.mctank.domain.McTankCart;
import company18.mctank.domain.McTankOrder;

@Service
public class CartService {
	private OrderManager<McTankOrder> orderManager;
	private ItemsService itemsService;
	private FuelWarningEventPublisher fuelWarning;


	public CartService(OrderManager<McTankOrder> orderManager, ItemsService itemsService) {
		this.orderManager = orderManager;
		this.itemsService = itemsService;
	}
	
	public boolean buy(McTankCart cart, Optional<UserAccount> userAccount, PaymentMethod payMethod) {
		McTankOrder order = getOrder(cart, userAccount);
		if (order == null)
			return false;
		
		// set paymentmethod
		order.setPaymentMethod(payMethod);
		this.orderManager.payOrder(order);
		
		// set order state to completed
		try {
			this.orderManager.completeOrder(order);
		} catch (Exception e) {
			this.orderManager.cancelOrder(order);
			return false;
		}
		
		//save order
		this.orderManager.save(order);
		
		// clear cart and redirect
		cart.clear();

		//EventPublisher checks the stock and publishes a warning if needed
		try {
			fuelWarning.checkStock();
		}catch (NullPointerException e){
			System.out.println("Catched NullPointer");
		}

		return true;
	}

	public boolean save(McTankCart cart) {
		McTankOrder order = getOrder(cart, Optional.of(cart.getOwner()));
		if (order == null)
			return false;

		//save order
		this.orderManager.save(order);;
		return true;
	}

	public boolean load(McTankCart cart, UserAccount userAccount) {
		McTankOrder openOrder = this.orderManager.findBy(userAccount)
				.stream()
				.filter(mcTankOrder ->
						mcTankOrder.getOrderStatus() == OrderStatus.OPEN)
				.findFirst()
				.orElse(null);
		if (openOrder == null)
			return false;
		openOrder.getOrderLines()
				.forEach(orderLine ->
						itemsService.getProduct(orderLine.getProductIdentifier())
							.ifPresent(product -> cart.addOrUpdateItem(product, orderLine.getQuantity())));
		return true;
	}

	private McTankOrder getOrder(McTankCart cart, Optional<UserAccount> userAccount) {
		McTankOrder order;
		// check for userAccount
		if (!userAccount.isPresent())
			return null;

		//creating new Order attached to account
		order = new McTankOrder(userAccount.get());

		// add items to order
		cart.addItemsTo(order);
		return order;
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
