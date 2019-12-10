package company18.mctank.controller;


import company18.mctank.exception.FuelStorageFullException;
import company18.mctank.service.RefillInventoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class RefillInventoryController {

	@Autowired
	private RefillInventoryService service;

	public String refillFuel(String name, double amount){
		try {
			service.refillFuel(name, amount);
		}
		catch (FuelStorageFullException e){

		}
		return "";
	}
}
