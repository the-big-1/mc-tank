package company18.mctank.initializer;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.quantity.Metric;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import company18.mctank.repository.ItemsRepository;






@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class ItemsInitializer implements DataInitializer {
	
	private final ItemsRepository itemsRepository;
	
	
	public ItemsInitializer(ItemsRepository itemsRepository) {
		
		Assert.notNull(itemsRepository, "Items can't be Null!");
		
		this.itemsRepository = itemsRepository;
	}
	
	
	@Override
	public void initialize() {
		var fuel_1 = new Product("Super Benzin", Money.of(1.33, "EUR"), Metric.LITER);
		var fuel_2 = new Product("Diesel", Money.of(1.29, "EUR"), Metric.LITER);

		var prod1 = new Product("Cola 0,5L", Money.of(3.00, "EUR"));
		var prod2 = new Product("Cola 0,2L", Money.of(1.50, "EUR"));
		var prod3 = new Product("McBig", Money.of(5.00, "EUR"));
		var prod4 = new Product("Basiswäsche", Money.of(4.50, "EUR"));

		fuel_1.addCategory("McTapf");
		fuel_2.addCategory("McTapf");

		prod1.addCategory("McSit");
		prod2.addCategory("McDrive");
		prod3.addCategory("McSit");
		prod4.addCategory("McWash");

		itemsRepository.save(fuel_1);
		itemsRepository.save(fuel_2);

		itemsRepository.save(prod1);
		itemsRepository.save(prod2);
		itemsRepository.save(prod3);
		itemsRepository.save(prod4);
	}
}