package company18.mctank.service;

import company18.mctank.repository.ItemsRepository;

import org.salespointframework.catalog.Product;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.springframework.beans.factory.annotation.Autowired;

public class RefillInventoryService {

	@Autowired
	private ItemsRepository items;

	@Autowired
	private UniqueInventory<UniqueInventoryItem> inventory;

	public void orderFuel(double amountToOrderBenzin, double amountToOrderDiesel /*double amountToOrderE10*/){ //new Params for more fuel types
		//increase different fuels with the amount
		//not more than 50.000l
		//addToInventory() 3times for 3 different fuels
		refillInventory("Benzin", amountToOrderBenzin);
		refillInventory("Diesel", amountToOrderDiesel);
	}

	public void refillInventory(String prodName, double amount){
		Product product = ((Product) items.findByName(prodName));
		inventory.findByProduct(product).map((item) -> item.increaseQuantity(product.createQuantity(amount + item.getQuantity().getAmount().doubleValue())));
	}

	/*public *DATATYPE* FuelWarning(){
	* if (benzinamount < 10.000l) return warning
	* else nothing
	*
	 */

	//EventListener ??
}
