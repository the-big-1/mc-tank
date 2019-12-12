package company18.mctank.service;

import javax.money.MonetaryAmount;

import org.salespointframework.catalog.Product;
import org.salespointframework.quantity.Metric;
import org.salespointframework.quantity.Quantity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import company18.mctank.domain.GasPump;
import company18.mctank.repository.ItemsRepository;

@Service
public class GasPumpService {
	private GasPump pump;
	
	private ItemsRepository itemsRepository;
	
	public GasPumpService(ItemsRepository itemsRepository) {
		this.itemsRepository = itemsRepository;	
	}
	
	public void setPump(int number) {
		try {
			this.pump = new RestTemplate().getForObject("https://jannusch.xyz/gasoline_pump/"+number, GasPump.class);
		}
		catch (Exception e) {
			this.pump = null;
		}
	}
	
	public boolean isInValid() {
		return this.pump == null;
	}
	
	public Product getFuel() {
		String productName;
		if(this.pump.getFuelType().equals("diesel fuel"))
			productName = "Diesel";
		else productName = "Super Benzin";
		return this.itemsRepository.findByName(productName).get().findFirst().get();
	}
	
	public float getFuelQuantity() {
		return this.pump.getFuelQuantity();
	}
	
	public MonetaryAmount getPrice() {
		return this.getFuel().getPrice().multiply(this.pump.getFuelQuantity());
	}
}
