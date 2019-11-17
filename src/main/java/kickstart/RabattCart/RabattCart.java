package kickstart.RabattCart;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;
import org.salespointframework.order.Cart;
import org.springframework.stereotype.Component;

@Component
public class RabattCart extends Cart {
	public RabattCart(){
		this.addOrUpdateItem(new Product("Produkt", Money.of(12.99, "EUR")), 1);
	}

	public void addDiscount(String rabattCode) {
		if ("McFive".equals(rabattCode))
			this.addOrUpdateItem(new Product("McFive", super.getPrice().multiply(0.05).negate()), 1);
		if ("McTen".contentEquals(rabattCode))
			this.addOrUpdateItem(new Product("McTen", super.getPrice().multiply(0.10).negate()), 1);
	}
}
