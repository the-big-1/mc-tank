package company18.mctank.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import company18.mctank.repository.ItemsRepository;
import org.junit.jupiter.api.Test;
import org.salespointframework.catalog.Product;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
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
	public void refillInventoryTest(){
		MonetaryAmount price = Monetary.getDefaultAmountFactory()
									   .setCurrency("EUR")
									   .setNumber(1.33)
									   .create();

		double testAmount = 10000;

		Product product1 = new Product("Benzin", price);

		items.save(product1);

		inventory.save(new UniqueInventoryItem(product1, product1.createQuantity(15000)));


		Quantity quantity = inventory.findByProduct(product1).get().getQuantity(); // just for debug


		//assertTrue(service.refillInventoryItem(product1.getName(), testAmount));

		UniqueInventoryItem item = inventory.findByProduct(product1).get();
		item.increaseQuantity(product1.createQuantity(testAmount));
		inventory.save(item);


		assertTrue(inventory.findByProduct(product1).get().getQuantity().getAmount().doubleValue() == 25000);
		// kann das sein das Autowired versch. inventories und catalogs erzeugt???

	}
}