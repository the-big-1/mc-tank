package company18.mctank.domain;


import javax.money.MonetaryAmount;



import org.javamoney.moneta.function.MonetaryOperators;
import org.salespointframework.catalog.Product;
import org.salespointframework.order.Cart;
import org.salespointframework.order.CartItem;
import org.springframework.stereotype.Component;



@Component
public class McTankCart extends Cart{
	public McTankCart(){}

	

public void McPointBonus(){
	
	// counts the number of categories in the cart to calculate discount
	int[] countCat = new int[4];
	float discount = 0;
	
	for(CartItem i: this ){
		if(i.getProduct().getCategories().toString() == "McZapf" && countCat[0] == 0){
			countCat[0] += 1;
			discount += 0.05;
		}
		if(i.getProduct().getCategories().toString() == "McDrive" && countCat[1] == 0){
			countCat[1] += 1;
			discount += 0.05;
		}
		if(i.getProduct().getCategories().toString() == "McSit" && countCat[2] == 0){
			countCat[2] += 1;
			discount += 0.05;
		}
		if(i.getProduct().getCategories().toString() == "McWash" && countCat[3] == 0){
			countCat[3] += 1;
			discount += 0.05;
		}
	}
		this.addOrUpdateItem(new Product("McPoint Bonus", super.getPrice().multiply(discount).negate()), 1);
		//super.getPrice().multiply(discount).negate();
}
	// rounds Carts getPrice()
	@Override
	public MonetaryAmount getPrice() {
		return super.getPrice().with(MonetaryOperators.rounding());
	}


	public void addDiscount(String discountCode){
		if ("McTen".equals(discountCode)  && !this.containsDiscount("Registration Bonus")) {
			//super.getPrice().multiply(0.10).negate();
			this.addOrUpdateItem(new Product("Registration Bonus", super.getPrice().multiply(0.10).negate()), 1);

	}}

	public boolean containsDiscount(String discountCode){
		// every code can only be used once
		for (CartItem item: this.toList()){
			if(discountCode.equals(item.getProductName())){
				return true;
			}
		}
		return false;
	}
}


