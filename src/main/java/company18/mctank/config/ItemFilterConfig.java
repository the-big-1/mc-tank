package company18.mctank.config;

import org.salespointframework.inventory.LineItemFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemFilterConfig {

	/**
	 * Filters products with negative prices to not have their quantities reducted in the inventory (claims or discounts).
	 * @return false if items quantity is not supposed to be reduced in inventory
	*/
	@Bean
	LineItemFilter filter() {
		return item -> !item.getPrice().isNegative();
	}
}
