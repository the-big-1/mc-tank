package company18.mctank.service;

import company18.mctank.forms.NewItemForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ItemsServiceUnitTest {

	@Autowired
	private ItemsService service;

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
    }

    @Test
    void testFindProduct() {
    }

    @Test
    void getProductQuantity() {
    }

    @Test
    void updateProductQuantity() {
    }

    @Test
    void findByCategory() {
    }

    @Test
    void findBestProducts() {
    }

    @Test
    void getFuelFuture() {
    }
}