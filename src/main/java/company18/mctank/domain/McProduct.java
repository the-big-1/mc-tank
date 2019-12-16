package company18.mctank.domain;

import org.salespointframework.catalog.Product;
import org.salespointframework.quantity.Quantity;

public class McProduct {
	Quantity quantity;
	Product product;
	int orders;

	public McProduct(Product product, Quantity quantity, int orders) {
		this.quantity = quantity;
		this.product = product;
		this.orders = orders;
	}

	public Quantity getQuantity() {
		return quantity;
	}

	public Product getProduct() {
		return product;
	}

	public int getOrders() {
		return orders;
	}
}
