package kickstart.CatalogPrototyp;

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
		var price = form.getPrice();
		
		return catalog.save(new Product(name, price));
	}
	
}