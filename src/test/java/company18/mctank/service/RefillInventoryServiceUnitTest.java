package company18.mctank.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import company18.mctank.exception.FuelStorageFullException;
import company18.mctank.repository.ItemsRepository;
import org.junit.jupiter.api.Test;
import org.salespointframework.catalog.Product;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
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

		double testAmount = 10000;

		Product product1 = new Product("Super Benzin Test", price);

		items.save(product1);

		inventory.save(new UniqueInventoryItem(product1, product1.createQuantity(15000)));



		assertTrue(service.refillInventoryItem(product1.getName(), testAmount));

		UniqueInventoryItem item = inventory.findByProduct(product1).get();
		item.increaseQuantity(product1.createQuantity(testAmount));
		inventory.save(item);


		assertTrue(inventory.findByProduct(product1).get().getQuantity().getAmount().doubleValue() == 25000);

		assertFalse(service.refillInventoryItem("Snickers", testAmount)); // Item not in Inventory/Catalog


		// kann das sein das Autowired versch. inventories und catalogs erzeugt???

	}

	@Test
	public void refillFuelTest(){
		MonetaryAmount price = Monetary.getDefaultAmountFactory()
				.setCurrency("EUR")
				.setNumber(1.33)
				.create();

		double failAmount = 50000;
		double failAmount2 = 35000;

		Product product3 = new Product("Benzin", price);
		Product product4 = new Product("Diesel", price);


		items.save(product3);
		items.save(product4);


		inventory.save(new UniqueInventoryItem(product3, product3.createQuantity(15000)));
		inventory.save(new UniqueInventoryItem(product4, product4.createQuantity(15000)));


		try {
			service.refillFuel(product3.getName(), failAmount);
		}
		catch (FuelStorageFullException e){
			assertThat(e.getClass().equals(new FuelStorageFullException()));
		}

		try {
			service.refillFuel(product4.getName(), failAmount2);
		}
		catch (FuelStorageFullException e){
			assertThat(e.getClass().equals(new FuelStorageFullException()));
		}

	}
}