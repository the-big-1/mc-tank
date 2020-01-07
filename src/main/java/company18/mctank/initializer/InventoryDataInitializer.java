package company18.mctank.initializer;

import company18.mctank.service.GasPumpService;
import org.salespointframework.catalog.Product;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import company18.mctank.repository.ItemsRepository;

/**
 * Initializer for inventory.
 * Implements {@link DataInitializer}.
 *
 * @author CS
 * @author ArtemSer
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class InventoryDataInitializer implements DataInitializer {
	private static final Logger LOG = LoggerFactory.getLogger(InventoryDataInitializer.class);

	private final UniqueInventory<UniqueInventoryItem> inventoryRepository;
	private final ItemsRepository itemsRepository;
	private final GasPumpService gasPumpService;

	public InventoryDataInitializer(UniqueInventory<UniqueInventoryItem> inventoryRepository,
									ItemsRepository itemsRepository, GasPumpService gasPumpService) {

		Assert.notNull(inventoryRepository, "Inventory must not be null");
		Assert.notNull(itemsRepository, "Items must not be null");

		this.inventoryRepository = inventoryRepository;
		this.itemsRepository = itemsRepository;
		this.gasPumpService = gasPumpService;
	}

	/**
	 * Initializes inventory. 
	 * Every {@link Product} out of {@link ItemsRepository} gets a {@link Quantity} of 100 depending on products metric unit. 
	 */
	@Override
	@Order(Ordered.LOWEST_PRECEDENCE)
	public void initialize() {

		// dont initialize if already populated
		if (this.inventoryRepository.findAll().iterator().hasNext()) {
			return;
		}
		
		LOG.info("Initializing: Inventory");
		for (Product product : this.itemsRepository.findAll()) {
			if (inventoryRepository.findByProduct(product).isEmpty()) {
				// if product is not present create new inventory entry with quantity 100
				Quantity amount;
					amount = product.createQuantity(100);
				UniqueInventoryItem item = new UniqueInventoryItem(product, amount);
				inventoryRepository.save(item);
			} else {
				// else update existing quantity to 100
				UniqueInventoryItem item = inventoryRepository.findByProduct(product).get();
				item.increaseQuantity(
						product.createQuantity(
								100 - 
								item.getQuantity()
								.getAmount()
								.doubleValue()));
			}
		}
		LOG.info("Initializing: Inventory. Done.");
	}

}
