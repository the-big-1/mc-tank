package company18.mctank.service;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.function.MonetaryOperators;
import org.salespointframework.catalog.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import company18.mctank.domain.GasPump;
import company18.mctank.repository.ItemsRepository;

/**
 * Service to get data from gas pump api.
 * Holds one {@link GasPump}.
 * @author CS
 *
 */
@Service
public class GasPumpService {
	private GasPump pump;
	
	private ItemsRepository itemsRepository;
	
	public GasPumpService(ItemsRepository itemsRepository) {
		this.itemsRepository = itemsRepository;	
		this.pump = null;
	}
	
	/**
	 * Gets {@link GasPump}.
	 * @param number number of gas pump to be accessed
	 */
	public void setPump(int number) {
		try {
			this.pump = new RestTemplate().getForObject("https://jannusch.xyz/gasoline_pump/"+number, GasPump.class);
		} catch (Exception e) {
			this.pump = null;
		}
	}
	
	/**
	 * Checks if pump is invalid.
	 * @return true if pump is invalid
	 */
	public boolean isInValid() {
		return this.pump == null;
	}
	
	/**
	 * Gets first {@link Product} from {@link ItemsRepository} with matching name.
	 * @return Fuel as {@link Product} 
	 */
	public Product getFuel() {
		String productName;
		if (this.pump.getFuelType().equals("diesel fuel"))
			productName = "Diesel";
		else productName = "Super Benzin";
		return this.itemsRepository.findByName(productName).get().findFirst().get();
	}
	
	/**
	 * Getter for fuels quantity.
	 * @return fuel quantity rounded to two decimals
	 */
	public float getFuelQuantity() {
		return (float)(((int)(this.pump.getFuelQuantity()*100))/100.0);
	}
	
	/**
	 * Gets fuels price multiplied by its quantity.
	 * @return money needed to buy the fuel from gas pump
	 */
	public MonetaryAmount getPrice() {
		return this.getFuel().getPrice()
				.multiply(this.getFuelQuantity())
				.with(MonetaryOperators.rounding());
	}
}
