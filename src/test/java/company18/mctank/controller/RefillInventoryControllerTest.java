package company18.mctank.controller;

import static org.junit.jupiter.api.Assertions.*;

import company18.mctank.domain.GasPump;
import company18.mctank.forms.RequestFuelBody;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class RefillInventoryControllerTest {

	@Autowired
	RefillInventoryController controller;

	@Test
	void refillFuels() {
		RequestFuelBody requestFuelBody = new RequestFuelBody();
		requestFuelBody.setAmount(10);
		requestFuelBody.setFuelType(GasPump.DIESEL);
		assertEquals(controller.refillFuels(requestFuelBody), ResponseEntity.ok().build());
	}
}