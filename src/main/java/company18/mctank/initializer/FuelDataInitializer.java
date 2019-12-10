package company18.mctank.initializer;


import company18.mctank.repository.ItemsRepository;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Metric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class FuelDataInitializer implements DataInitializer {

	@Autowired
	private ItemsRepository items;

	@Autowired
	private UniqueInventory<UniqueInventoryItem> inventory;

	@Override
	public void initialize(){

		/*var fuel_1 = new Product("Super Benzin", Money.of(1.33, "EUR"), Metric.LITER);
		var fuel_2 = new Product("Diesel", Money.of(1.29, "EUR"), Metric.LITER);

		fuel_1.addCategory("McZapf");
		fuel_2.addCategory("McZapf");

		items.save(fuel_1);
		items.save(fuel_2);

		inventory.save(new UniqueInventoryItem(fuel_1, fuel_1.createQuantity(1000)));
		inventory.save(new UniqueInventoryItem(fuel_2, fuel_2.createQuantity(1000)));

		 */
	}
}
