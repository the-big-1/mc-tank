package company18.mctank.service;

import company18.mctank.domain.Customer;
import company18.mctank.domain.McTankCart;
import company18.mctank.domain.McTankOrder;
import org.salespointframework.catalog.Product;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.PaymentMethod;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service to turn the session of a cart into an order and handle pay function.
 *
 * @author vivien
 * @author ArtemSer
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
		if (cart.getCustomer() == null || cart.get().count() == 0)
			return false;

		McTankOrder order = getOrder(cart);
		
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
		

		//RefillService checks the stock and publishes a warning if needed
//		 refillService.checkStock();

		return true;
	}

	public boolean save(McTankCart cart) {
		McTankOrder order = getOrder(cart);

		//save order
		this.orderManager.save(order);;
		return true;
	}

	public boolean load(McTankCart cart, UserAccount userAccount) {
		McTankOrder openOrder = getOpenOrder(userAccount);
		if (openOrder == null)
			return false;
		openOrder.getOrderLines()
				.forEach(orderLine ->
						itemsService.getProduct(orderLine.getProductIdentifier())
							.ifPresent(product -> cart.addOrUpdateItem(product, orderLine.getQuantity())));
		return true;
	}

	private McTankOrder getOrder(McTankCart cart) {
		Customer customer = cart.getCustomer();
		final McTankOrder openOrder = getOpenOrder(customer.getUserAccount());

		if (openOrder == null) { //creating new Order attached to account
			McTankOrder newOrder = new McTankOrder(customer.getUserAccount());
			cart.addItemsTo(newOrder);
			return newOrder;
		} else {
			cart.get().forEach(cartItem -> {
				if (openOrder.getOrderLines().filter(orderLine -> orderLine.getProductIdentifier().equals(cartItem.getProduct().getId())).isEmpty()) {
					openOrder.addOrderLine(cartItem.getProduct(), cartItem.getQuantity());
				}
			});
			return openOrder;
		}
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


	private McTankOrder getOpenOrder(UserAccount userAccount) {
		return this.orderManager.findBy(userAccount)
				.stream()
				.filter(mcTankOrder ->
						mcTankOrder.getOrderStatus() == OrderStatus.OPEN)
				.findFirst()
				.orElse(null);
	}

}
