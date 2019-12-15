package company18.mctank.controller;


import company18.mctank.exception.FuelStorageFullException;
import company18.mctank.service.RefillInventoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class RefillInventoryController {

	@Autowired
	private RefillInventoryService service;

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
