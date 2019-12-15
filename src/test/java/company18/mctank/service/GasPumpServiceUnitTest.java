package company18.mctank.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.money.MonetaryAmount;

import org.junit.jupiter.api.Test;
import org.salespointframework.catalog.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GasPumpServiceUnitTest {
	@Autowired 
	GasPumpService service;
	
	@Test
	void testService() {
		
		// test invalid pump number
		this.service.setPump(13);
		assertTrue(this.service.isInValid());
		
		// setting pump new until we get valid results
		while(this.service.isInValid())
			this.service.setPump(10);
		
		// test results
		assertTrue(this.service.getFuelQuantity() > 0);
		assertTrue(this.service.getFuel() instanceof Product);
		assertTrue(this.service.getPrice() instanceof MonetaryAmount);
	}

}
