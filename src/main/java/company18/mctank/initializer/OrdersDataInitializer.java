package company18.mctank.initializer;

import company18.mctank.domain.McTankCart;
import company18.mctank.domain.McTankOrder;
import company18.mctank.repository.ItemsRepository;
import company18.mctank.service.CartService;
import company18.mctank.service.CustomerService;
import org.salespointframework.catalog.Product;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.payment.Cash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


/**
 * Initializes default user accounts and customers. The following are created:
 * <ul>
 * <li>An admin user named "boss".</li>
 * <li>The customers "hans", "dextermorgan", "earlhickey", "mclovinfogell" backed by user accounts with the same
 * name.</li>
 * </ul>
 *
 * @author Oliver Gierke
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
class OrdersDataInitializer implements DataInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(OrdersDataInitializer.class);

	@Autowired
	private CartService cartService;
	@Autowired
	private ItemsRepository itemsRepository;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private OrderManagement<McTankOrder> orderManager;


	/*
	 * (non-Javadoc)
	 * @see org.salespointframework.core.DataInitializer#initialize()
	 */
	@Override
	public void initialize() {

		// dont initialize if already populated
		if (!this.orderManager.findAll(Pageable.unpaged()).isEmpty()) {
			return;
		}
		McTankCart cart = new McTankCart();
		LOG.info("Initializing: orders");

		for (Product product : this.itemsRepository.findAll()) {
			cart.setCustomer(customerService.getCustomer("test"));
			cartService.addOrUpdateItem(cart, product, product.createQuantity(2));
			cartService.buy(cart, Cash.CASH);
			cart.clear();
		}
		LOG.info("Initializing: orders. Done.");
	}
}
