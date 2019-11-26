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
	
	public Product createNewProduct(NewItemForm form) {  // takes the values from the given form, modifies them and creats a new product
		var name = form.getProductName();
		
		String priceAsString = form.getPrice().replace(",", ".").replace("€", "");   // delete/replace all unwanted chars
		double priceToDouble = Double.parseDouble(priceAsString);					 // convert to Double
		MonetaryAmount price = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(priceToDouble).create();  //create MonetaryAmount   .setNumber() requests Double
		
		var product = new Product(name, price);
		
		if(form.getProductCategories() != null) {
		for (String newCategory: form.getProductCategories()) {
			product.addCategory(newCategory);
		}
		}
		
		
		return items.save(product);    //save new Product in items
	}
	
}