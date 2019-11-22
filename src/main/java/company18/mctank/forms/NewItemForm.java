package company18.mctank.forms;

import javax.validation.constraints.NotEmpty;

class NewItemForm {

	@NotEmpty(message = "Name darf nicht leer sein.") //
	private String productName;

	@NotEmpty(message = "Preis darf nicht leer sein.") //
	private String price;
	
	

	public NewItemForm(String productName, String price) {

		this.productName = productName;
		this.price = price;
	}

	public String getProductName() {
		return productName;
	}


	public String getPrice() {
		return price;
	}

}
