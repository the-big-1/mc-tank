package company18.mctank.forms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestFuelBodyUnitTest {

	public RequestFuelBody testBody = new RequestFuelBody("Benzine", 100);

	@Test
	void testEmptyConstructor(){
		assertNotNull(new RequestFuelBody());
	}

    @Test
    void testToString() {
		System.out.println(testBody.toString());

		assertTrue(testBody.toString().equals("RequestFuelBody{fuelType='Benzine', amount=100}"));
    }

    @Test
    void getFuelType() {
    	assertEquals(testBody.getFuelType(),"Benzine");
    }

    @Test
    void setFuelType() {
    	testBody.setFuelType("Diesel");
    	assertEquals(testBody.getFuelType(), "Diesel");

		testBody.setFuelType("Benzine");

	}

    @Test
    void getAmount() {
    	assertEquals(testBody.getAmount(), 100);
    }

    @Test
    void setAmount() {
		testBody.setAmount(120);
		assertEquals(testBody.getAmount(), 120);

		testBody.setAmount(100);

	}
}