package company18.mctank.domain;

import org.junit.jupiter.api.Test;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import static org.junit.jupiter.api.Assertions.*;

class McTankCartUnitTest {

	McTankCart testCart = new McTankCart();

    @Test
    void getMcPointBonus() {
    	assertEquals(testCart.getMcPointBonus(),0);
    }

    @Test
    void getPrice() {
		MonetaryAmount expectedPrice = Monetary.getDefaultAmountFactory()
				.setCurrency("EUR")
				.setNumber(0)
				.create();

		assertEquals(testCart.getPrice(), expectedPrice);
    }

    @Test
    void addDiscount() {
    }

    @Test
    void containsDiscount() {
    }

    @Test
    void clear() {
    	testCart.clear();

    	assertTrue(testCart.isEmpty());
    	assertNull(testCart.getCustomer());
    }

    @Test
    void getCustomer() {
    	assertNull(testCart.getCustomer());
    }

    @Test
    void setCustomer() {
    }
}