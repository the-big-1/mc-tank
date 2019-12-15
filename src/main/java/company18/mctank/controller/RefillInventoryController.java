package company18.mctank.controller;


import company18.mctank.exception.FuelStorageFullException;
import company18.mctank.service.RefillInventoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Refill inventory controller.
 */
@Controller
public class RefillInventoryController {

	@Autowired
	private RefillInventoryService service;

	/**
	 * Refill fuels.
	 *
	 * @param amountBenzine benzine amount
	 * @param amountDiesel diesel amount
	 * @return string
	 */
	@GetMapping("/orderfuel")
	public String refillFuels(double amountBenzine, double amountDiesel){
		try {
			service.refillFuels(amountBenzine, amountDiesel);
		}
		catch (FuelStorageFullException e){
			//send message that order is to large
		}
		return "";
	}
}
