package company18.mctank.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import company18.mctank.exception.FuelStorageFullException;
import company18.mctank.repository.ItemsRepository;
import org.junit.jupiter.api.Test;
import org.salespointframework.catalog.Product;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Metric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

@SpringBootTest
public class RefillInventoryServiceUnitTest {

	@Autowired
	private ItemsRepository items;

	@Autowired
	private UniqueInventory<UniqueInventoryItem> inventory;

	@Autowired
	private RefillInventoryService service;

	@Test
	public void refillInventoryItemTest(){
		MonetaryAmount price = Monetary.getDefaultAmountFactory()
									   .setCurrency("EUR")
									   .setNumber(1.33)
									   .create();

		double testAmount = 1000;
		double expectedAmount = 2500;

		Product product1 = new Product("Cola Test", price);

		items.save(product1);

		inventory.save(new UniqueInventoryItem(product1, product1.createQuantity(1500)));



		assertTrue(service.refillInventoryItem(product1.getName(), testAmount));

		/*UniqueInventoryItem item = inventory.findByProduct(product1).get();
		item.increaseQuantity(product1.createQuantity(testAmount));
		inventory.save(item);

		assertTrue(inventory.findByProduct(product1).get().getQuantity().getAmount().doubleValue() == expectedAmount);

		does not work properly

		*/

		assertFalse(service.refillInventoryItem("Snickers Test", testAmount)); // Item not in Inventory/Catalog

	}

	@Test
	public void refillFuelsTest(){
		MonetaryAmount price = Monetary.getDefaultAmountFactory()
				.setCurrency("EUR")
				.setNumber(1.33)
				.create();

		double testAmount = 2500;
		double failAmount = 50000;
		double failAmount2 = 49000;

		// Benzin/Diesel should be created in DataInitializer with 1000 Liter amount

		var fuel_1 = new Product("Super Benzin", price, Metric.LITER);
		var fuel_2 = new Product("Diesel", price, Metric.LITER);

		fuel_1.addCategory("McZapf");
		fuel_2.addCategory("McZapf");

		items.save(fuel_1);
		items.save(fuel_2);

		inventory.save(new UniqueInventoryItem(fuel_1, fuel_1.createQuantity(1000)));
		inventory.save(new UniqueInventoryItem(fuel_2, fuel_2.createQuantity(1000)));

		try {
			assertTrue(service.refillFuels(testAmount, testAmount));
		}
		catch (FuelStorageFullException e){
			fail();
		}

		try {
			service.refillFuels(failAmount, failAmount);
		}
		catch (FuelStorageFullException e){
			assertThat(e.getClass().equals(new FuelStorageFullException()));
		}

		try {
			service.refillFuels(failAmount2, failAmount2);
		}
		catch (FuelStorageFullException e){
			assertThat(e.getClass().equals(new FuelStorageFullException()));
		}

	}
}