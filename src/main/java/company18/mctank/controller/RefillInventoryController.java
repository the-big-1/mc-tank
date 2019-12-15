package company18.mctank.controller;


import company18.mctank.exception.FuelStorageFullException;
import company18.mctank.service.RefillInventoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


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
	 * @param amountBenzin binzin amount
	 * @param amountDiesel diesel amount
	 * @return string
	 */
	public String refillFuels(double amountBenzin, double amountDiesel){
		try {
			service.refillFuels(amountBenzin, amountDiesel);
		}
		catch (FuelStorageFullException e){
			//send message that order is to large
		}
		return "";
	}
}
