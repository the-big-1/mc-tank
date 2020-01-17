package company18.mctank.service;

import company18.mctank.domain.Customer;
import company18.mctank.domain.McTankCart;
import company18.mctank.domain.McTankOrder;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

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


	public CartService(OrderManager<McTankOrder> orderManager,
					   ItemsService itemsService,
					   RefillInventoryService refillService) {

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
		if (cart.getCustomer() == null || cart.get().count() == 0) {
			return false;
		}
		McTankOrder order = new McTankOrder(cart.getCustomer().getUserAccount());
		cart.addItemsTo(order);
		// set order state to completed
		try {
			// set paymentmethod
			order.setPaymentMethod(payMethod);
			this.orderManager.payOrder(order);
			this.orderManager.completeOrder(order);
			//save order if completed
			this.orderManager.save(order);
		} catch (Exception e) {
			// else cancel 
			this.orderManager.cancelOrder(order);
			LOGGER.error(e.getMessage());
			return false;
		}

		return true;
	}

	public boolean save(McTankCart cart) {
		McTankOrder order = getOrder(cart);
		Optional<McTankOrder> oldOrder = this.orderManager.findBy(order.getUserAccount()).stream()
											.filter(mcTankOrder ->
											mcTankOrder.getOrderStatus() == OrderStatus.OPEN).findFirst();
		if (oldOrder.isPresent())
			this.orderManager.delete(oldOrder.get());
		//save order
		this.orderManager.save(order);
		
		return true;
	}

	public boolean load(McTankCart cart, UserAccount userAccount) {
		McTankOrder openOrder = getOpenOrder(userAccount);
		if (openOrder == null) {
			return false;
		}
		openOrder.getOrderLines()
				.forEach(orderLine ->
						itemsService.getProduct(orderLine.getProductIdentifier())
							.ifPresent(product -> this.addOrUpdateItem(cart, product, orderLine.getQuantity())));
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
			Product negatedProduct = new Product(product.getName().concat(" RECLAMATION"), product.getPrice().negate());
			cart.addOrUpdateItem(negatedProduct, amount);
		} else {
			this.addOrUpdateItem(cart, product, product.createQuantity(amount));
		}
	}
	
	public void addOrUpdateItem(McTankCart cart, Product product, Quantity amount) {
		 Optional<CartItem> prodInCart;
		 Quantity possible = this.itemsService.getProductQuantity(product);
		 // if product already in cart
		 if ((prodInCart = cart.get().filter((item) -> item.getProduct().getId().equals(product.getId())).findFirst()).isPresent()){
			 // check if new amount exceeds inventory
			 if (prodInCart.get().getQuantity().add(amount).isGreaterThan(possible))
				 // if so remove and replace by new order with maximum possible amount
				 cart.removeItem(prodInCart.get().getId());
			 	 if (possible.isGreaterThan(product.createQuantity(0)))
			 		 cart.addOrUpdateItem(product, possible);
			 	 return;
		 }
		 if (amount.isGreaterThan(possible)) {
			 if (possible.isGreaterThan(product.createQuantity(0)))
				 cart.addOrUpdateItem(product, possible);
		 }
		 else
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
