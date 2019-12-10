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


	public boolean refillFuels(double amountBenzin, double amountDiesel) throws FuelStorageFullException{
		var benzinObj = items.findByName("Super Benzin")
							  .stream()
							  .findFirst();

		var dieselObj = items.findByName("Diesel")
				.stream()
				.findFirst();

		if (benzinObj.isEmpty() || dieselObj.isEmpty()){
			return false;
		}
		Product benzin = benzinObj.get();
		Product diesel = dieselObj.get();

		var benzinOpt = inventory.findByProduct(benzin)
									 .stream()
									 .findFirst();

		var dieselOtp = inventory.findByProduct(diesel)
				.stream()
				.findFirst();

		UniqueInventoryItem benzinItem = benzinOpt.get();
		UniqueInventoryItem dieselItem = dieselOtp.get();


		double currentamountBenzin = benzinItem.getQuantity()
								   		 .getAmount()
								   		 .doubleValue();

		double currentamountDiesel = dieselItem.getQuantity()
				.getAmount()
				.doubleValue();

		if (amountBenzin + currentamountBenzin > 50000 || amountDiesel + currentamountDiesel > 50000){
			throw new FuelStorageFullException();
		}

		benzinItem.increaseQuantity(benzin.createQuantity(amountBenzin));
		dieselItem.increaseQuantity(diesel.createQuantity(amountDiesel));

		return true;
	}


	public double getFuelAmountBenzin(){
		var benzinObj = items.findByName("Super Benzin")
							 .stream()
							 .findFirst();

		Product benzin = benzinObj.get();

		var benzinOpt = inventory.findByProduct(benzin)
								 .stream()
								 .findFirst();

		UniqueInventoryItem benzinItem = benzinOpt.get();

		double currentamountBenzin = benzinItem.getQuantity()
				.getAmount()
				.doubleValue();

		return currentamountBenzin;
	}

	public double getFuelAmountDiesel(){
		var dieselObj = items.findByName("Diesel")
							 .stream()
							 .findFirst();

		Product diesel = dieselObj.get();

		var dieselOpt = inventory.findByProduct(diesel)
								 .stream()
								 .findFirst();

		UniqueInventoryItem dieselItem = dieselOpt.get();

		double currentamountDiesel = dieselItem.getQuantity()
				.getAmount()
				.doubleValue();

		return currentamountDiesel;
	}
}
