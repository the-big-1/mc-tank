package company18.mctank.service;

import com.mysema.commons.lang.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GasPumpServiceUnitTest {
	@Autowired 
	GasPumpService gasPumpService;

	// TODO: Needed Mock. Without Mock we cannot check random data from API -> it will fall
	
//	@Test
//	void testService() {
//
//		// test invalid pump number
//		assertTrue(this.gasPumpService.isInValid(13));
//
//		// setting pump new until we get valid results
//		while(this.gasPumpService.isInValid())
//			this.gasPumpService.setPump(10);
//
//		// test results
//		assertTrue(this.gasPumpService.getFuelQuantity() > 0);
//		assertTrue(this.gasPumpService.getFuel() instanceof Product);
//		assertTrue(this.gasPumpService.getPrice() instanceof MonetaryAmount);
//	}

	@Test
	void testArrayOfPumps(){
		Assert.notNull(gasPumpService.getPumps(), "Needed Array");
	}

}
