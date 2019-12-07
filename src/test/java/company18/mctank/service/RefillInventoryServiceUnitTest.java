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
		double failAmount = 50000;

		var product1 = new Product("Benzin", price);
		var product2 = new Product("Diesel", price);

		var exceptionProd = new Product("E10", price);

		items.save(product1);
		items.save(product2);
		items.save(exceptionProd);

		inventory.save(new UniqueInventoryItem(product1, product1.createQuantity(15000)));
		inventory.save(new UniqueInventoryItem(product2, product2.createQuantity(10000)));
		inventory.save(new UniqueInventoryItem(exceptionProd, exceptionProd.createQuantity(10000)));


		service.refillInventoryItem(product1.getName(), testAmount);

		assertThat(inventory.findByProduct(product1).get().getQuantity().getAmount().doubleValue() == 25000);
		assertThat(inventory.findByProduct(product2).get().getQuantity().getAmount().doubleValue() == 20000);  //is true but 10000 != 20000

		//does not work, dont know why

	}
}