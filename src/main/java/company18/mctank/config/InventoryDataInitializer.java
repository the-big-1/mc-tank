package company18.mctank.config;

import org.salespointframework.core.DataInitializer;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.springframework.stereotype.Component;

@Component
public class InventoryDataInitializer implements DataInitializer {

	private final UniqueInventory<UniqueInventoryItem> inv;
	
	public InventoryDataInitializer(UniqueInventory<UniqueInventoryItem> inv) {
		this.inv = inv;
	}
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

}
