package company18.mctank.domain;


import javax.money.MonetaryAmount;
import javax.persistence.Entity;

import org.salespointframework.order.Order;
import org.salespointframework.order.OrderIdentifier;
import org.salespointframework.useraccount.UserAccount;

import java.util.Objects;

/**
 * Entity representing an order at McTank. Extends {@link Order}.
 * Implements {@link Comparable} for sorting from latest to earliest.
 *
 * @author ArtemSer
 * @author CS
 */
@Entity
public class McTankOrder extends Order implements Comparable<McTankOrder>{
	
	@SuppressWarnings("unused")
	private McTankOrder() {};
	
	public McTankOrder(UserAccount account) {
		super(account);
	}

	public String getIdString(){
		return Objects.requireNonNull(super.getId()).getIdentifier();
	}

	public String getTotalString(){
		return super.getTotal().toString();
	}

	@Override
	public int compareTo(McTankOrder o) {
		boolean isThisOrderEarlier = o.getDateCreated().isAfter(this.getDateCreated());
		boolean isThisOrderLater = this.getDateCreated().isAfter(o.getDateCreated());
		if (isThisOrderLater) {
			return -1;
		} else if (isThisOrderEarlier) {
			return 1;
		} else {
			return 0;
		}
	}
}
