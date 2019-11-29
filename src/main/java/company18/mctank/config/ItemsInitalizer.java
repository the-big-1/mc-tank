package company18.mctank.config;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.quantity.Metric;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import company18.mctank.repository.Items;






@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class ItemsInitializer implements DataInitializer {
	
	private final Items items;
	
	
	public ItemsInitializer(Items items) {
		
		Assert.notNull(items, "Items can't be Null!");
		
		this.items = items;
	}
	
	
	@Override
	public void initialize() {
		var prod1 = new Product("Cola 0,5L", Money.of(3.00, "EUR"));
		var prod2 = new Product("Cola 0,2L", Money.of(1.50, "EUR"));
		var prod3 = new Product("Super Benzin", Money.of(100, "EUR"), Metric.LITER);
		var prod4 = new Product("McBig", Money.of(5.00, "EUR"));
		var prod5 = new Product("Basiswäsche", Money.of(4.50, "EUR"));
		
		prod1.addCategory("McSit");
		prod2.addCategory("McDrive");
		prod3.addCategory("McZapf");
		prod4.addCategory("McSit");
		prod5.addCategory("McWash");
		
		items.save(prod1);
		items.save(prod2);
		items.save(prod3);
		items.save(prod4);
		items.save(prod5);
	}
}