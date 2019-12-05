package company18.mctank.service;

import static org.assertj.core.api.Assertions.*;

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
	public void refillInventoryTest(){
		MonetaryAmount price = Monetary.getDefaultAmountFactory()
				.setCurrency("EUR")
				.setNumber(1.33)
				.create();

		double testAmount = 10000;

		var product1 = new Product("Benzin", price);
		var product2 = new Product("Diesel", price);

		items.save(product1);
		items.save(product2);

		inventory.save(new UniqueInventoryItem(product1, product1.createQuantity(15000)));
		inventory.save(new UniqueInventoryItem(product2, product2.createQuantity(10000)));

		//service.refillInventoryItem(product1.getName(), testAmount);

		//assertThat(inventory.findByProduct(product1).get().getQuantity() == product1.createQuantity(25000));
		//assertThat(inventory.findByProduct(product2).get().getQuantity() == product1.createQuantity(20000));

	}
}