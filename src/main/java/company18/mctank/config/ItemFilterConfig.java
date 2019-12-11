package company18.mctank.config;

import org.salespointframework.inventory.LineItemFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemFilterConfig {

	
	@Bean
	LineItemFilter filter() {
		// filters products with negative prices to not have their quantities reducted in the inventory (claims or discounts)
		return item -> !item.getPrice().isNegative();
	}
}
