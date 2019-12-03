package company18.mctank.initializer;

import java.util.Iterator;

import org.salespointframework.catalog.Product;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import company18.mctank.repository.ItemsRepository;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class InventoryDataInitializer implements DataInitializer {

	private final UniqueInventory<UniqueInventoryItem> inv;
	
	private final ItemsRepository itemsRepository;
	
	public InventoryDataInitializer(UniqueInventory<UniqueInventoryItem> inv, ItemsRepository itemsRepository) {
		Assert.notNull(inv, "Inventory must not be null");
		Assert.notNull(itemsRepository, "Items must not be null");
		
		this.inv = inv;
		this.itemsRepository = itemsRepository;
	}
	
	@Override
	public void initialize() {
		Iterator<Product> iterator = this.itemsRepository.findAll().iterator();
		while (iterator.hasNext()) {
			Product product = iterator.next();
			// save new uniqueinventoryitem if product  not in inventory
			if (!inv.findByProduct(product).isPresent())
				inv.save(new UniqueInventoryItem(product, product.createQuantity(100)));
			// else update quantity to 100
			else inv.findByProduct(product).get().increaseQuantity(product.createQuantity(100.0 - inv.findByProduct(product).get().getQuantity().getAmount().doubleValue()));
		}
	}

}
