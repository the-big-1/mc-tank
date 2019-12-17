package company18.mctank.service;

import company18.mctank.exception.FuelStorageFullException;
import company18.mctank.repository.ItemsRepository;

import org.salespointframework.catalog.Product;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Service for refilling the amount of {@link Product}s in the {@link UniqueInventory}.
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
	private ApplicationEventPublisher publisher;

	/**
	 * Refills the stock for any offered Item.
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
		inventory.save(item);
		return true;
	}

	/**
	 * @return Current amount of Benzine.
	 */
	public double getFuelAmountBenzine(){
		var benzineObj = items.findByName("Super Benzin")
							 .stream()
							 .findFirst();

		Product benzine = benzineObj.get();

		var benzineOpt = inventory.findByProduct(benzine)
								 .stream()
								 .findFirst();

		UniqueInventoryItem benzineItem = benzineOpt.get();

		double currentamountBenzine = benzineItem.getQuantity()
				.getAmount()
				.doubleValue();

		return currentamountBenzine;
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
