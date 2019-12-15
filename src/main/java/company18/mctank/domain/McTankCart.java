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

@Component
public class McTankCart extends Cart{

	public McTankCart(){}

	private Customer customer;

	/**
	 * Get mcPoint bonus.
	 * It is bonus calculated by count of mcPoints visited bu customer.
	 * If customer visits mcZapf and mcSit - its 2 mcPoints
	 *
	 * @return count of mcPoints
	 */
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
	 * Add discount to cart.
	 * It is amount depends on total price in cart.
	 *
	 * @param discountCode discount code
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
	 * Checks if cart already contains discount.
	 *
	 * @param discount discount
	 * @return true if cart contains this discount
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

	/**
	 * {@inheritDoc}
	 * Clears all item from cart and customer.
	 */
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


