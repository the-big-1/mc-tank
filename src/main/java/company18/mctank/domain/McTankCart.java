package company18.mctank.domain;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.money.MonetaryAmount;



import org.javamoney.moneta.function.MonetaryOperators;
import org.salespointframework.catalog.Product;
import org.salespointframework.order.Cart;
import org.salespointframework.order.CartItem;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Component;



@Component
public class McTankCart extends Cart{
	public McTankCart(){}

	

public void McPointBonus(){
	
	// deletes old bonus if existing
	String mcPointBonusStr = "McPoint Bonus";
	if (this.containsDiscount(mcPointBonusStr)) {
		for (CartItem item : this.toList()) {
			if (item.getProductName().equals(mcPointBonusStr))
				this.removeItem(item.getId());
		}
	}
	
	// counts the number of categories in the cart to calculate discount
	List<String> listedCategories = new LinkedList<String>();
	Streamable<String> currentCategories;
	for(CartItem i: this){
		currentCategories= i.getProduct().getCategories();
		for(String cat : currentCategories) {
			if (!listedCategories.contains(cat))
				listedCategories.add(cat);
		}
	}
	
	this.addOrUpdateItem(new Product(mcPointBonusStr, this.getPrice().multiply(listedCategories.size()*0.05).negate()), 1);
		//super.getPrice().multiply(discount).negate();
}
	// rounds Carts getPrice()
	@Override
	public MonetaryAmount getPrice() {
		return super.getPrice().with(MonetaryOperators.rounding());
	}


	public void addDiscount(String discountCode){
		Map<String, Integer> discountCodes = new HashMap<String, Integer>();
		discountCodes.put("McTen", 10);
		discountCodes.put("McFive", 5);
		discountCodes.put("Registration Bonus", 10);
		
		for (Map.Entry<String, Integer> entry : discountCodes.entrySet()) {
			if (entry.getKey().equals(discountCode)  && !this.containsDiscount(entry.getKey())) {
				this.addOrUpdateItem(new Product(entry.getKey(), this.getPrice().multiply(entry.getValue()/100.0).negate()), 1);
			}
		}
	}

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


