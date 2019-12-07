package company18.mctank.service;

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


	public void refillInventoryItem(String prodName, double amount){
		var productObjekt = items.findByName(prodName).stream().findFirst();
		Product product = productObjekt.get();			//can throw exception
		inventory.findByProduct(product).map((item) -> item.increaseQuantity(product.createQuantity(amount + item.getQuantity().getAmount().doubleValue())));
	}

	public void refillInventoryItems(HashMap<String, Double> products){
		for(Map.Entry<String, Double> entry : products.entrySet()){
			var productObjekt = items.findByName(entry.getKey()).stream().findFirst();
			Product product = productObjekt.get();			//can throw exception
			inventory.findByProduct(product).map((item) -> item.increaseQuantity(product.createQuantity(entry.getValue() + item.getQuantity().getAmount().doubleValue())));
		}
	}
}
