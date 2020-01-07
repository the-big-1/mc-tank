package company18.mctank.forms;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

/**
 * Form for adding a new {@link org.salespointframework.catalog.Product}s to the {@link company18.mctank.repository.ItemsRepository}.
 *
 * POJO class with Getters and Setters.
 *
 * @author David Leistner
 */

public class NewItemForm {

	@NotEmpty
	private String productName;

	@NotEmpty
	private String price;
	
	private List<String> productCategories;
	
	
	public NewItemForm(String productName, String price, List<String> productCategories) {

		this.productName = productName;
		this.price = price;
		this.productCategories = productCategories;
	}

	public String getProductName() {
		return productName;
	}

	public String getPrice() {
		return price;
	}
	
	public List<String> getProductCategories(){
		return productCategories;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setProductCategories(List<String> productCategories) {
		this.productCategories = productCategories;
	}
}
