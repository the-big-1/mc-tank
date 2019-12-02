package company18.mctank.domain;

import javax.money.MonetaryAmount;


import org.javamoney.moneta.Money;
import org.javamoney.moneta.function.MonetaryOperators;
import org.salespointframework.catalog.Product;
import org.salespointframework.order.Cart;
import org.salespointframework.order.CartItem;
import org.springframework.stereotype.Component;



@Component
public class DiscountCart extends Cart{
	public DiscountCart(){
	
		// adds a product to the cart with the given parameters
		
		//this.addOrUpdateItem(new Product("McZapf", Money.of(12.99, "EUR")),1);
		//this.addOrUpdateItem(new Product("McDrive", Money.of(12.99, "EUR")),1);
		
		// counts the number of categories in the cart to calculate discount
		int[] countCat = new int[4];
		float discount = 0;
		
		for(CartItem i : this)
		{
			if(i.getProductName().toString() == "McZapf" && countCat[0] == 0)
			{
				countCat[0] += 1;
				discount += 0.05;
			}
			if(i.getProductName().toString() == "McDrive" && countCat[1] == 0)
			{
				countCat[1] += 1;
				discount += 0.05;
			}
			if(i.getProductName().toString() == "McSit" && countCat[2] == 0)
			{
				countCat[2] += 1;
				discount += 0.05;
			}
			if(i.getProductName().toString() == "McWash" && countCat[3] == 0)
			{
				countCat[3] += 1;
				discount += 0.05;
			}
		}
		

			this.addOrUpdateItem(new Product("McTest", super.getPrice().multiply(discount).negate()),1);


	}
	
	
	// rounds Carts getPrice()
	@Override
	public MonetaryAmount getPrice() {
		return super.getPrice().with(MonetaryOperators.rounding());
	}

	   

	public void addDiscount(String discountCode){
		// add discount codes to cart as a product
		// TODO : Rewrite this method (Normal total price update)
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


