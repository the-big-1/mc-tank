package company18.mctank.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GasPumpUnitTest {

	GasPump testpump = new GasPump();

    @Test
    void getNumber() {
    	assertEquals(testpump.getNumber(), 0);
    }

    @Test
    void getFuelType() {
    	assertNotNull(testpump.getFuelType());
    }

    @Test
    void getFuelQuantity() {
    	assertEquals(testpump.getFuelQuantity(), 0.0f);
    }
}