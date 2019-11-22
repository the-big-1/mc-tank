package company18.mctank.service;

import company18.mctank.repository.Items;
import company18.mctank.forms.NewItemForm;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import org.salespointframework.catalog.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ItemsService{
	
	private final Items items;
	
	public ItemsService(Items items) {
		this.items = items;
	}
	
	public Product createNewProduct(NewItemForm form) {
		var name = form.getProductName();
		String priceAsString = form.getPrice().replace(",", ".");
		double priceToDouble = Double.parseDouble(priceAsString);
		MonetaryAmount price = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(priceToDouble).create();
		
		return items.save(new Product(name, price));
	}
	
}