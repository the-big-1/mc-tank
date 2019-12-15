package company18.mctank.service;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Predicate;

import company18.mctank.domain.Discount;
import company18.mctank.factory.DiscountFactory;
import org.salespointframework.catalog.Product;
import org.salespointframework.order.CartItem;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.PaymentMethod;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import company18.mctank.domain.McTankCart;
import company18.mctank.domain.McTankOrder;

/**
 * Service to turn the session of a cart into an order and handle pay function.
 * @author vivien
 *
 */
@Service
public class CartService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);

	private OrderManager<McTankOrder> orderManager;
	private ItemsService itemsService;
	private RefillInventoryService refillService;


	public CartService(OrderManager<McTankOrder> orderManager, ItemsService itemsService, RefillInventoryService refillService) {
		this.orderManager = orderManager;
		this.itemsService = itemsService;
		this.refillService = refillService;
	}
	
	/**
	 * 
	 * @param cart can only be paid if there is a user account and pay method.
	 * @param payMethod
	 * @return whether the cart can be turned to an order.
	 */
	public boolean buy(McTankCart cart, PaymentMethod payMethod) {
		if (cart.getCustomer() == null)
			return false;

		McTankOrder order = getOrder(cart,
				Optional.of(cart.getCustomer().getUserAccount()));
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
			LOGGER.error(e.getMessage());
			return false;
		}

		//save order
		this.orderManager.save(order);
		
		// clear cart and redirect
		cart.clear();

		//RefillService checks the stock and publishes a warning if needed
		refillService.checkStock();

		return true;
	}

	public boolean save(McTankCart cart) {
		McTankOrder order = getOrder(cart, Optional.of(cart.getCustomer().getUserAccount()));
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
