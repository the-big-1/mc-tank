package company18.mctank.service;

import company18.mctank.controller.CartController;
import company18.mctank.controller.OverviewController;
import company18.mctank.domain.FuelWarningEvent;
import company18.mctank.exception.FuelStorageFullException;
import company18.mctank.repository.ItemsRepository;

import org.salespointframework.catalog.Product;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
	private ApplicationEventPublisher publisher;

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

			//publish event if warning was true event set warning to false
			publishEvent();
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

		System.out.println("b1: " + benzinObj);

		Product benzin = benzinObj.get();

		var benzinOpt = inventory.findByProduct(benzin)
								 .stream()
								 .findFirst();

		System.out.println("b1: " + benzinOpt);

		UniqueInventoryItem benzinItem = benzinOpt.get();

		double currentamountBenzin = benzinItem.getQuantity()
				.getAmount()
				.doubleValue();

		System.out.println("b3: " + currentamountBenzin);

		return currentamountBenzin;
	}

	/**
	 * @return Current amount of diesel fuel.
	 */

	public double getFuelAmountDiesel(){
		var dieselObj = items.findByName("Diesel")
							 .stream()
							 .findFirst();

		System.out.println("d1: " + dieselObj);

		Product diesel = dieselObj.get();

		var dieselOpt = inventory.findByProduct(diesel)
								 .stream()
								 .findFirst();

		System.out.println("d2: " + dieselOpt);

		UniqueInventoryItem dieselItem = dieselOpt.get();

		double currentamountDiesel = dieselItem.getQuantity()
				.getAmount()
				.doubleValue();

		System.out.println("d3: " + currentamountDiesel);

		return currentamountDiesel;
	}

	public void publishEvent(){
		FuelWarningEvent event = new FuelWarningEvent(this);
		publisher.publishEvent(event);
	}

	public void checkStock(){
		System.out.println("B: " + getFuelAmountBenzin());
		System.out.println("D: " + getFuelAmountDiesel());

		if(getFuelAmountBenzin() < 10.000 || getFuelAmountDiesel() < 10.000){
			publishEvent();
		}

		//service functions returns NullPointerException don't know exactly why because tests work
		//problem with products
		//goes probably back to initializer

		//ring dependency

	}
}
