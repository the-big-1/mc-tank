package company18.mctank.service;

import company18.mctank.repository.ItemsRepository;
import company18.mctank.forms.NewItemForm;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import org.salespointframework.catalog.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
public class ItemsService{
	@Autowired
	private ItemsRepository itemsRepository;

	
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
		
		
		return itemsRepository.save(product);    //save new Product in items
	}

	public Map<String, List<Product>> makeAssortment(String[] mcPoints) {
		Map<String, List<Product>> assortmentMap = new HashMap<>();
		for (String point : mcPoints){
			assortmentMap.put(point, itemsRepository.findByCategory(point).toList());
		}
		return assortmentMap;
	}
}