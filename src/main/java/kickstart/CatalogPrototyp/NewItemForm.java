package kickstart.CatalogPrototyp;

import javax.money.MonetaryAmount;
import javax.validation.constraints.NotEmpty;

class NewItemForm {

	@NotEmpty(message = "Name darf nicht leer sein.") //
	private final String productName;

	@NotEmpty(message = "Preis darf nicht leer sein.") //
	private final MonetaryAmount price;


	public NewItemForm(String productName, MonetaryAmount price) {

		this.productName = productName;
		this.price = price;
	}

	public String getProductName() {
		return productName;
	}
	
	public MonetaryAmount getPrice() {
		return price;
	}
}

