package company18.mctank.service;

import company18.mctank.exception.FuelStorageFullException;
import company18.mctank.repository.ItemsRepository;

import org.salespointframework.catalog.Product;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RefillInventoryService {

	@Autowired
	private ItemsRepository items;

	@Autowired
	private UniqueInventory<UniqueInventoryItem> inventory;


	public boolean refillInventoryItem(String prodName, double amount){
		var productObj = items.findByName(prodName)
				 				 .stream()
								 .findFirst();
		
		if(productObj.isEmpty()) {
			return false;
		}
		Product product = productObj.get();
		
		if(inventory.findByProduct(product).isEmpty()) {
			return false;
		}

		UniqueInventoryItem item = inventory.findByProduct(product).get();

		item.increaseQuantity(product.createQuantity(amount));

		return true;
	}


	public boolean refillFuel(String prodName, double amount) throws FuelStorageFullException{
		var productObj = items.findByName(prodName)
							  .stream()
							  .findFirst();

		if (productObj.isEmpty()){
			return false;
		}
		Product product = productObj.get();

		if(inventory.findByProduct(product).isEmpty()) {
			return false;
		}

		var inventoryItem = inventory.findByProduct(product)
									 .stream()
									 .findFirst();

		UniqueInventoryItem item = inventoryItem.get();

		double currentamount = item.getQuantity()
								   .getAmount()
								   .doubleValue();

		if (amount + currentamount > 50000){
			throw new FuelStorageFullException();
		}

		item.increaseQuantity(product.createQuantity(amount));
		return true;
	}

	/*public void refillInventoryItems(HashMap<String, Double> products){
		for(Map.Entry<String, Double> entry : products.entrySet()){
			var productObjekt = items.findByName(entry.getKey()).stream().findFirst();
			Product product = productObjekt.get();			//can throw exception
			inventory.findByProduct(product).map((item) -> item.increaseQuantity(product.createQuantity(entry.getValue())));
		}
	}*/
}
