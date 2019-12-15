package company18.mctank.domain;


import java.util.LinkedList;
import java.util.List;

import javax.money.MonetaryAmount;


import org.javamoney.moneta.function.MonetaryOperators;
import org.salespointframework.catalog.Product;
import org.salespointframework.order.Cart;
import org.salespointframework.order.CartItem;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Component;

/**
 * McTankCart as a component of Cart in Salespoint.
 * @author vivien
 *
 */

@Component
public class McTankCart extends Cart{

	private Customer customer;

	public int getMcPointBonus() {
		
		// counts the number of categories in the cart to calculate discount
		List<String> listedCategories = new LinkedList<>();
		Streamable<String> currentCategories;
		for(CartItem i: this) {
			currentCategories= i.getProduct().getCategories();
			for(String cat : currentCategories) {
				if (!listedCategories.contains(cat))
					listedCategories.add(cat);
			}
		}

		return listedCategories.size();

	}

	
	// rounds Carts getPrice()
	@Override
	public MonetaryAmount getPrice() {
		return super.getPrice().with(MonetaryOperators.rounding());
	}


	/**
	 * 
	 * @param discountCode each user gets a certain discount code once when registrated
	 */
	public void addDiscount(String discountCode){
		if (discountCode.length() < Discount.VALID_DISCOUNT_LENGTH)
			return;

		for (Discount discount: this.customer.getDiscounts()) {
			if (discount.getStatus() == Discount.DiscountStatus.AVAILABLE &&
					discount.getId().toString().startsWith(discountCode) &&
					!this.containsDiscount(discount)) {
				Product product = new Product(discount.getDiscountProductName(),
												discount.getDiscountPrice(this.getPrice()));
				this.addOrUpdateItem(product, 1);
			}
		}
	}

	/**
	 * 
	 * @param discount 
	 * @return if the code has already been used.
	 */
	public boolean containsDiscount(Discount discount){
		// every code can only be used once
		String discountName = discount.getDiscountProductName();
		for (CartItem item: this.toList()) {
			if (discountName.equals(item.getProductName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void clear() {
		super.clear();
		this.customer = null;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


}


