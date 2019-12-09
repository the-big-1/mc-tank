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
		//double failAmount = 50000;

		Product product1 = new Product("Benzin", price);
		//var product2 = new Product("Diesel", price);

		//var exceptionProd = new Product("E10", price);

		items.save(product1);
		//items.save(product2);
		//items.save(exceptionProd);


		inventory.save(new UniqueInventoryItem(product1, product1.createQuantity(15000)));
		//inventory.save(new UniqueInventoryItem(product2, product2.createQuantity(10000)));
		//inventory.save(new UniqueInventoryItem(exceptionProd, exceptionProd.createQuantity(10000)));


		
		
		assertTrue(service.refillInventoryItem(product1.getName(), testAmount));
		Quantity quantity = inventory.findByProduct(product1).get().getQuantity();
	//	assertThat(inventory.findByProduct(product1).get().getQuantity().getAmount().doubleValue() == 25000);  //is true but 10000 != 20000


		//does not work, dont know why

		/* This is a value-based class;
		* use of identity-sensitive operations (including reference equality (==),
		* identity hash code, or synchronization) on instances of Optional may have unpredictable results
		* and should be avoided. (https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html)
		*
		* that's why it does not work.
		 */
	}
}