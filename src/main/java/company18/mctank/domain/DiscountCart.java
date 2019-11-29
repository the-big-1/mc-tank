package company18.mctank.domain;

import org.javamoney.moneta.Money;

import org.salespointframework.catalog.Product;
import org.salespointframework.order.Cart;
import org.salespointframework.order.CartItem;
import org.springframework.stereotype.Component;

@Component
public class DiscountCart extends Cart{
	public DiscountCart(){
		// adds a product to the cart with the given parameters
		this.addOrUpdateItem(new Product("Product", Money.of(12.99, "EUR")),1);
	}

	public void addDiscount(String discountCode){
		// add discount codes to cart as a product
		if("McFive".equals(discountCode) && !this.containsDiscount("McFive"))
			this.addOrUpdateItem(new Product("McTen", super.getPrice().multiply(0.05).negate()),1);
		if("McTen".equals(discountCode) && !this.containsDiscount("McTen"))
			this.addOrUpdateItem(new Product("McTen", super.getPrice().multiply(0.10).negate()),1);
	}

	public boolean containsDiscount(String discountCode){
		// every code can only be used once
		for (CartItem item: this.toList()){
			if(discountCode.equals(item.getProductName()))
				return true;
		}
		return false;
	}
}


