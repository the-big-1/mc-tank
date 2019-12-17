package company18.mctank.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import company18.mctank.exception.FuelStorageFullException;
import company18.mctank.repository.ItemsRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.salespointframework.catalog.Product;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Metric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

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

		assertFalse(service.refillInventoryItem("Snickers Test", testAmount)); // Item not in Inventory/Catalog
	}
}