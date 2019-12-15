package company18.mctank.service;

import javax.annotation.PostConstruct;
import javax.money.MonetaryAmount;

import org.javamoney.moneta.function.MonetaryOperators;
import org.salespointframework.catalog.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import company18.mctank.domain.GasPump;
import company18.mctank.repository.ItemsRepository;

@Service
public class GasPumpService {
	private static final int TOTAL_GAS_STATIONS = 12;
	public static final String API_URL = "https://jannusch.xyz/gasoline_pump/";

	private GasPump[] pumps = new GasPump[TOTAL_GAS_STATIONS];

	private ItemsRepository itemsRepository;
	
	public GasPumpService(ItemsRepository itemsRepository) {
		this.itemsRepository = itemsRepository;	
	}

	@PostConstruct
	public void pumpsInit() {
		for (int i=0; i<TOTAL_GAS_STATIONS; ++i) {
			try {
				this.pumps[i] = new RestTemplate().getForObject(API_URL + i, GasPump.class);
			} catch (Exception e) {
				this.pumps[i] = null;
			}
		}
	}

	public GasPump[] getPumps() {
		return this.pumps;
	}

	public boolean isInValid(int pumpNumber) {
		return this.pumps[pumpNumber] == null;
	}
	
	public Product getFuel(int pumpNumber) {
		if (this.pumps[pumpNumber] == null)
			return null;
		String productName = this.pumps[pumpNumber].getFuelType();
		return this.itemsRepository.findByName(productName).get()
				.findFirst().get();
	}
	
	public float getFuelQuantity(int pumpNumber) {
		if (this.pumps[pumpNumber] == null)
			return 0f;
		return this.pumps[pumpNumber].getFuelQuantity();
	}
	
	public MonetaryAmount getPrice(int pumpNumber) {
		return this.getFuel(pumpNumber).getPrice()
				.multiply(this.getFuelQuantity(pumpNumber))
				.with(MonetaryOperators.rounding());
	}
}
