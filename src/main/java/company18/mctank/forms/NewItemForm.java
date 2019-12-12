package company18.mctank.forms;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

public class NewItemForm {

	@NotEmpty(message = "Name darf nicht leer sein.") //
	private String productName;

	@NotEmpty(message = "Preis darf nicht leer sein.") //
	private String price;
	
	private List<String> productCategories = new ArrayList<String>();
	
	
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
	
	public void setProductCategories(List<String> productCategories) {
		this.productCategories = productCategories;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setPrice(String price) {
		this.price = price;
	}
}
