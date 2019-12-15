package company18.mctank.service;

import company18.mctank.controller.CartController;
import company18.mctank.controller.OverviewController;
import company18.mctank.exception.FuelStorageFullException;
import company18.mctank.repository.ItemsRepository;

import org.salespointframework.catalog.Product;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Service for refilling the {@link Product}s in the {@link UniqueInventory}.
 * Especially for the Fuels.
 *
 * @author David Leistner
 */

@Service
public class RefillInventoryService {

	@Autowired
	private ItemsRepository items;

	@Autowired
	private UniqueInventory<UniqueInventoryItem> inventory;

	@Autowired
	private CartController cart;

	@Autowired
	private OverviewController overview;

	/**
	 * Refills the stock of fuels.
	 *
	 * @param amountBenzin which should be added to the current amount of Benzine.
	 * @param amountDiesel which should be added to the current amount of Diesel.
	 *
	 * @throws FuelStorageFullException if the amount is bigger than the size of the Inventory.
	 */

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

		if (amountBenzin + currentamountBenzin > 50.000 || amountDiesel + currentamountDiesel > 50.000){
			throw new FuelStorageFullException();
		}

		if (amountBenzin + currentamountBenzin > 10.000 && amountDiesel + currentamountDiesel > 10.000){
			//reset FuelWarning to false if Depot holds more than 10.000 Liter each
			cart.setFuelWarning(false);
			overview.setFuelWarning(false);
		}

		benzinItem.increaseQuantity(benzin.createQuantity(amountBenzin));
		dieselItem.increaseQuantity(diesel.createQuantity(amountDiesel));

		return true;
	}

	/**
	 * Refills the stock for any offered Item, except Fuel.
	 * @param prodName of the Items to add.
	 * @param amount of which the stock should be refilled with.
	 */

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

	/**
	 * @return Current amount of Benzine.
	 */

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

	/**
	 * @return Current amount of diesel fuel.
	 */

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
