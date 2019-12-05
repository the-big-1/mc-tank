package company18.mctank.initializer;

import org.salespointframework.catalog.Product;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import company18.mctank.repository.ItemsRepository;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class InventoryDataInitializer implements DataInitializer {

	private final UniqueInventory<UniqueInventoryItem> inventoryRepository;

	private final ItemsRepository itemsRepository;

	public InventoryDataInitializer(UniqueInventory<UniqueInventoryItem> inventoryRepository,
									ItemsRepository itemsRepository) {

		Assert.notNull(inventoryRepository, "Inventory must not be null");
		Assert.notNull(itemsRepository, "Items must not be null");

		this.inventoryRepository = inventoryRepository;
		this.itemsRepository = itemsRepository;
	}

	@Override
	public void initialize() {
		for (Product product : this.itemsRepository.findAll()) {
			if (inventoryRepository.findByProduct(product).isPresent()) {
				Quantity amount = product.createQuantity(100);
				UniqueInventoryItem item = new UniqueInventoryItem(product, amount);
				inventoryRepository.save(item);
			}
			else {
				inventoryRepository.findByProduct(product)
					.map(
						(item) -> item.increaseQuantity(
							product.createQuantity(
								100 - item
									.getQuantity()
									.getAmount()
									.doubleValue()
							)
						)
					);
			}
		}
	}

}
