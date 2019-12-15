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

	@Test
	public void refillFuelsTest(){

		double testAmount = 2500;
		double failAmount = 50000;
		double failAmount2 = 49000;

		// Benzine/Diesel created in DataInitializer with 100 Liter amount

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

	@Test
	void getFuelAmountBenzine() {
		double expectedAmount = 100;

		assertTrue(service.getFuelAmountBenzine() == expectedAmount);
	}

	@Test
	void getFuelAmountDiesel() {
		double expectedAmount = 100;
		assertTrue(service.getFuelAmountDiesel() == expectedAmount);

	}
}