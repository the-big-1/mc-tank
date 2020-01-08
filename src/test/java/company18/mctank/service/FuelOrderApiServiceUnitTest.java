package company18.mctank.service;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FuelOrderApiServiceUnitTest {
	@Test
	void postTest() {
		String order = "any order";
		assertTrue(FuelOrderApiService.post(order));
	}
	
	@Test
	void priceTest() {
		assertTrue(FuelOrderApiService.getBenzinePrice() > 0);
		assertTrue(FuelOrderApiService.getDieselPrice() > 0);
	}
}
