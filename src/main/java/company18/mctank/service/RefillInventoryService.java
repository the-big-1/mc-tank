package company18.mctank.service;

import company18.mctank.domain.FuelWarningEvent;
import company18.mctank.exception.FuelStorageFullException;
import company18.mctank.repository.ItemsRepository;

import org.salespointframework.catalog.Product;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

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
	private ApplicationEventPublisher publisher;

	/**
	 * Refills the stock of fuels.
	 *
	 * @param amountBenzine which should be added to the current amount of Benzine.
	 * @param amountDiesel which should be added to the current amount of Diesel.
	 *
	 * @throws FuelStorageFullException if the amount is bigger than the size of the Inventory.
	 */
	public boolean refillFuels(double amountBenzine, double amountDiesel) throws FuelStorageFullException{
		var benzineObj = items.findByName("Super Benzin")
							  .stream()
							  .findFirst();
		var dieselObj = items.findByName("Diesel")
				.stream()
				.findFirst();

		if (benzineObj.isEmpty() || dieselObj.isEmpty()){
			return false;
		}

		Product benzine = benzineObj.get();
		Product diesel = dieselObj.get();

		var benzineOpt = inventory.findByProduct(benzine)
									 .stream()
									 .findFirst();
		var dieselOtp = inventory.findByProduct(diesel)
							     .stream()
								 .findFirst();

		UniqueInventoryItem benzineItem = benzineOpt.get();
		UniqueInventoryItem dieselItem = dieselOtp.get();

		double currentamountBenzine = benzineItem.getQuantity()
											   .getAmount()
								   		       .doubleValue();
		double currentamountDiesel = dieselItem.getQuantity()
											   .getAmount()
											   .doubleValue();

		if (amountBenzine + currentamountBenzine > 50000.0 || amountDiesel + currentamountDiesel > 50000.0){
			throw new FuelStorageFullException();
		}

		if (amountBenzine + currentamountBenzine > 10000.0 && amountDiesel + currentamountDiesel > 10000.0){

			//reset warning to false
			publishEvent(false);
		}

		benzineItem.increaseQuantity(benzine.createQuantity(amountBenzine));
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

	public void publishEvent(boolean fuelWarning){
		FuelWarningEvent event = new FuelWarningEvent(this, fuelWarning);
		publisher.publishEvent(event);
	}

	public void checkStock(){
		if(getFuelAmountBenzine() < 10000.0 || getFuelAmountDiesel() < 10000.0){
			publishEvent(true);
		}
	}
}
