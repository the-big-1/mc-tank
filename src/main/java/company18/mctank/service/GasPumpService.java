package company18.mctank.service;

import javax.annotation.PostConstruct;
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
 * @author ArtemSer
 *
 */
@Service
public class GasPumpService {
	private static final int TOTAL_GAS_STATIONS = 12;
	public static final String API_URL = "https://jannusch.xyz/gasoline_pump/";

	private GasPump[] pumps = new GasPump[TOTAL_GAS_STATIONS];

	private ItemsRepository itemsRepository;
	
	public GasPumpService(ItemsRepository itemsRepository) {
		this.itemsRepository = itemsRepository;
	}

	/**
	 * Inits pump with API after bean init.
	 */
	@PostConstruct
	public void pumpsInit() {
		for (int i = 0; i < TOTAL_GAS_STATIONS; i++) {
			try {
				this.pumps[i] = new RestTemplate().getForObject(API_URL + (i+1), GasPump.class);
			} catch (Exception e) {
				this.pumps[i] = null;
			}
		}
	}

	/**
	 * Returns list of pumps.
	 *
	 * @return list of pumps
	 */
	public GasPump[] getPumps() {
		return this.pumps;
	}

	/**
	 * Checks if pump is invalid.
	 * @param pumpNumber represent number of pump
	 * @return true if pump is invalid
	 */
	public boolean isInValid(int pumpNumber) {
		return this.pumps[pumpNumber] == null;
	}

	/**
	 * Gets first {@link Product} from {@link ItemsRepository} with matching name.
	 * @param pumpNumber represent number of pump
	 * @return Fuel as {@link Product}
	 */
	public Product getFuel(int pumpNumber) {
		if (this.pumps[pumpNumber] == null)
			return null;
		String productName = this.pumps[pumpNumber].getFuelType();
		return this.itemsRepository.findByName(productName).get()
				.findFirst().get();
	}

	/**
	 * Getter for fuels quantity.
	 * @param pumpNumber represent number of pump
	 * @return fuel quantity rounded to two decimals
	 */
	public float getFuelQuantity(int pumpNumber) {
		if (this.pumps[pumpNumber] == null)
			return 0f;
		return this.pumps[pumpNumber].getFuelQuantity();
	}

	/**
	 * Gets fuels price multiplied by its quantity.
	 * @param pumpNumber represent number of pump
	 * @return money needed to buy the fuel from gas pump
	 */
	public MonetaryAmount getPrice(int pumpNumber) {
		return this.getFuel(pumpNumber).getPrice()
				.multiply(this.getFuelQuantity(pumpNumber))
				.with(MonetaryOperators.rounding());
	}
}
