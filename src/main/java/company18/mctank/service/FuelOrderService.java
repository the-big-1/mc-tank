package company18.mctank.service;

import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.springframework.beans.factory.annotation.Autowired;

public class FuelOrderService {

	@Autowired
	private UniqueInventory<UniqueInventoryItem> inventory;

	public void orderFuel(double amountToOrderBenzin, double amountToOrder /*double amountToOrderE10*/){  //datatype double may be changed later, unsure if E10 is sold
		//increase different fuels with the amount
		//not more than 50.000l 
	}
}
