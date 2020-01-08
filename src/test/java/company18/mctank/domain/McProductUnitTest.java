package company18.mctank.domain;

import org.junit.jupiter.api.Test;
import org.salespointframework.catalog.Product;
import org.salespointframework.quantity.Quantity;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import static org.junit.jupiter.api.Assertions.*;

class McProductUnitTest {

	MonetaryAmount price = Monetary.getDefaultAmountFactory()
			.setCurrency("EUR")
			.setNumber(1.33)
			.create();

	Product testProduct = new Product("test", price);

	McProduct testMcProduct = new McProduct(testProduct, Quantity.of(10), 10);

    @Test
    void getQuantity() {
    	assertEquals(testMcProduct.getQuantity(), Quantity.of(10));
    }

    @Test
    void getProduct() {
    	assertEquals(testMcProduct.getProduct(), testProduct);
    }

    @Test
    void getOrders() {
    	assertEquals(testMcProduct.getOrders(), 10);
    }
}