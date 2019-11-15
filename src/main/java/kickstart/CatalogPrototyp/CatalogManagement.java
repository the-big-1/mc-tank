package kickstart.CatalogPrototyp;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import org.salespointframework.catalog.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CatalogManagement{
	
	private final CatalogPrototyp catalog;
	
	public CatalogManagement(CatalogPrototyp catalog) {
		this.catalog = catalog;
	}
	
	public Product createNewProduct(NewItemForm form) {
		var name = form.getProductName();
		String priceAsString = form.getPrice().replace(",", ".");
		double priceToDouble = Double.parseDouble(priceAsString);
		MonetaryAmount price = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(priceToDouble).create();
		
		return catalog.save(new Product(name, price));
	}
	
}