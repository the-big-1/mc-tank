package company18.mctank.service;

import company18.mctank.forms.NewItemForm;
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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ItemsServiceUnitTest {

	@Autowired
	private ItemsService service;

	@Autowired
	private ItemsRepository items;

	@Autowired
	private UniqueInventory<UniqueInventoryItem> inventory;

    @Test
    void createNewProductWithForm() {
		NewItemForm testForm = new NewItemForm("TEST","1.39", new ArrayList<String>());

		assertThat(service.createNewProduct(testForm)).isNotNull();


	}

    @Test
    void testCreateNewProduct() {
		List<String> list = new ArrayList<String>();
		list.add("McZapf");
		list.add("McSit");
		list.add("McDrive");
		list.add("McWash");

		//empty inputs for name and cost/price already tested in form

		//testing different inputs for the cost/price
		assertThat(service.createNewProduct("Cola 0,5", "1.30", list)).isNotNull();  	//with .
		assertThat(service.createNewProduct("Cola 0,5", "1,30", list)).isNotNull();		//with ,
		assertThat(service.createNewProduct("Cola 0,5", "1.30 €", list)).isNotNull(); 	//with €
    }

    @Test
    void makeAssortment() {
		String[] mcPoints = {"McZapf", "McSit", "McDrive", "McWash"};

		assertThat(service.makeAssortment(mcPoints)).isNotNull();

	}

    @Test
    void getProduct() {
		MonetaryAmount price = Monetary.getDefaultAmountFactory()
				.setCurrency("EUR")
				.setNumber(1.33)
				.create();

		Product idtest = new Product("idtest", price);
		items.save(idtest);

		assertTrue(service.getProduct(idtest.getId()).get().equals(idtest));
    }

    @Test
    void getOrderMap() {
    	assertThat(service.getOrderMap()).isNotNull();
    }

    @Test
    void getQuantityMap() {
		assertThat(service.getQuantityMap()).isNotNull();
    }

    @Test
    void findProduct() {
		MonetaryAmount price = Monetary.getDefaultAmountFactory()
				.setCurrency("EUR")
				.setNumber(1.33)
				.create();

		Product invTest = new Product("invTest", price);
		items.save(invTest);

		UniqueInventoryItem item = new UniqueInventoryItem(invTest, invTest.createQuantity(100));
		inventory.save(item);

		assertThat(service.findProduct(invTest).get().equals(invTest));


    }

    @Test
    void findProductWithIdentifier() {
		MonetaryAmount price = Monetary.getDefaultAmountFactory()
				.setCurrency("EUR")
				.setNumber(1.33)
				.create();

		Product invTest2 = new Product("invTest2", price);
		items.save(invTest2);

		UniqueInventoryItem item = new UniqueInventoryItem(invTest2, invTest2.createQuantity(100));
		inventory.save(item);

		assertThat(service.findProduct(invTest2.getId()).get().equals(invTest2));
    }

    @Test
    void getProductQuantity() {
		MonetaryAmount price = Monetary.getDefaultAmountFactory()
				.setCurrency("EUR")
				.setNumber(1.33)
				.create();

		Product quantityTest = new Product("quantityTest", price);
		items.save(quantityTest);

		UniqueInventoryItem item = new UniqueInventoryItem(quantityTest, quantityTest.createQuantity(100));
		inventory.save(item);


    	assertThat(service.getProductQuantity(quantityTest)).isNotNull();
    }

    @Test
    void updateProductQuantity() {
    }

    @Test
    void findByCategory() {
		assertThat(service.findByCategory("McZapf")).isNotNull();
		assertThat(service.findByCategory("McZapf")).isNotEmpty();
    }

    @Test
    void findBestProducts() {
    	assertThat(service.findBestProducts()).isNotNull();
		assertThat(service.findBestProducts()).isNotEmpty();

	}

    @Test
    void getFuelFuture() {
    }
}