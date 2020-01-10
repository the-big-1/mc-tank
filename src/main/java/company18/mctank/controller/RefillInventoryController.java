package company18.mctank.controller;


import company18.mctank.domain.GasPump;
import company18.mctank.forms.RequestFuelBody;
import company18.mctank.service.FuelOrderApiService;
import company18.mctank.service.RefillInventoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Refill inventory controller.
 *
 * @author ArtemSer
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
		int amount = requestFuelBody.getAmount();
		serviceInventory.refillInventoryItem(productName, amount);
		FuelOrderApiService.post(productName + " " +  amount);
		return ResponseEntity.ok().build();
	}
	
	/**
	 * Gets price of fuel.
	 * @param fuelType string
	 * @return double 
	 */
	@GetMapping("/getFuelPrice")
	public ResponseEntity<Double> getFuelPrice(@RequestParam("fuelType") String fuelType){
		double price;
		if (fuelType.equals(GasPump.DIESEL))
			price = FuelOrderApiService.getDieselPrice();
		else price = FuelOrderApiService.getBenzinePrice();
		return ResponseEntity.ok(price);
	}
}
