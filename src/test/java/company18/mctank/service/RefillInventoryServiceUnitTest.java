package company18.mctank.service;

import static org.junit.jupiter.api.Assertions.*;

import company18.mctank.repository.ItemsRepository;
import org.junit.jupiter.api.Test;
import org.salespointframework.catalog.Product;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.util.Properties;

@SpringBootTest
public class RefillInventoryServiceUnitTest {

	@Autowired
	private ItemsRepository items;

	@Autowired
	private UniqueInventory<UniqueInventoryItem> inventory;

	@Autowired
	private RefillInventoryService service;

	@Test
	public void refillInventoryItemTest() {
		MonetaryAmount price = Monetary.getDefaultAmountFactory()
				.setCurrency("EUR")
				.setNumber(1.33)
				.create();

		double testAmount = 1000;

		Product product1 = new Product("Cola Test", price);
		Product notInventory = new Product("InCatalogButNotInInv", price);
		items.save(product1);
		items.save(notInventory);
		inventory.save(new UniqueInventoryItem(product1, product1.createQuantity(1500)));


		assertTrue(service.refillInventoryItem(product1.getName(), testAmount));

		assertFalse(service.refillInventoryItem("Snickers Test", 1)); // Item not in Catalog and Inventory

		assertFalse(service.refillInventoryItem("InCatalogButNotInInv", 1)); //Item in Catalog but not in Inventory
	}

	/*
	// work lokal but ont in jenkins
	
	@Test
	void getFuelAmountBenzineTest() {
		//Initialized with 100 Liter but 2 Liter taken in OrderInitializer for TestOrders
		assertTrue(service.getFuelAmountBenzine() == 98.0);
	}

	@Test
	void getFuelAmountDieselTest() {
		//Initialized with 100 Liter but 2 Liter taken in OrderInitializer for TestOrders
		assertTrue(service.getFuelAmountDiesel() == 98.0);
	}
	*/
}
