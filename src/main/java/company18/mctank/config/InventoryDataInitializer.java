package company18.mctank.config;

import java.util.Iterator;

import org.salespointframework.catalog.Product;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import company18.mctank.repository.Items;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class InventoryDataInitializer implements DataInitializer {

	private final UniqueInventory<UniqueInventoryItem> inv;
	
	private final Items items;
	
	public InventoryDataInitializer(UniqueInventory<UniqueInventoryItem> inv, Items items) {
		Assert.notNull(inv, "Inventory must not be null");
		Assert.notNull(items, "Items must not be null");
		
		this.inv = inv;
		this.items = items;
	}
	
	@Override
	public void initialize() {
		Iterator<Product> iterator = this.items.findAll().iterator();
		while (iterator.hasNext()) {
			Product product = iterator.next();
			// save new uniqueinventoryitem if product  not in inventory
			if (inv.findByProduct(product) != null)
				inv.save(new UniqueInventoryItem(product, product.createQuantity(100)));
			// else update quantity to 100
			else inv.findByProduct(product).map((item) -> item.increaseQuantity(product.createQuantity(100-item.getQuantity().getAmount().doubleValue())));
		}
	}

}
