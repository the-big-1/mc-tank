package company18.mctank.controller;


import company18.mctank.domain.GasPump;
import company18.mctank.forms.RequestFuelBody;
import company18.mctank.service.RefillInventoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Refill inventory controller.
 */
@Controller
public class RefillInventoryController {

	@Autowired
	private RefillInventoryService serviceInventory;

	/**
	 * Refill fuels.
	 *
	 * @param requestFuelBody request fuel body
	 * @return string
	 */
	@PostMapping("/orderfuel")
	public ResponseEntity<?> refillFuels(@RequestBody RequestFuelBody requestFuelBody) {
		String productName = requestFuelBody.getFuelType().equals(GasPump.DIESEL) ? GasPump.DIESEL : GasPump.SUPER_BENZIN;
		serviceInventory.refillInventoryItem(productName, requestFuelBody.getAmount());
		return ResponseEntity.ok().build();
	}
}
