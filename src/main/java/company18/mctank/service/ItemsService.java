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
public class ItemsService {
	@Autowired
	private ItemsRepository itemsRepository;


	public Product createNewProduct(NewItemForm form) {
		return  createNewProduct(form.getProductName(), form.getPrice(), form.getProductCategories());
	}

	public Product createNewProduct(String name, String cost, List<String> mcPoints){

		String priceAsString = cost
			.replace(",", ".")
			.replace("€", "")
			.replace(" ", "");

		double priceToDouble = Double.parseDouble(priceAsString);
		MonetaryAmount price = Monetary.getDefaultAmountFactory()
			.setCurrency("EUR")
			.setNumber(priceToDouble)
			.create();

		var product = new Product(name, price);

		if (mcPoints != null) {
			for (String point : mcPoints) {
				product.addCategory(point);
			}
		}


		return itemsRepository.save(product);
	}

	public Map<String, List<Product>> makeAssortment(String[] mcPoints) {
		Map<String, List<Product>> assortmentMap = new HashMap<>();

		for (String point : mcPoints) {
			assortmentMap.put(point, itemsRepository.findByCategory(point).toList());
		}

		return assortmentMap;
	}
}