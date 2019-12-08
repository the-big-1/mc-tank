package company18.mctank.domain;


import javax.persistence.Entity;

import org.salespointframework.order.Order;
import org.salespointframework.useraccount.UserAccount;

@Entity
public class McTankOrder extends Order{
	
	@SuppressWarnings("unused")
	private McTankOrder() {};
	
	public McTankOrder(UserAccount account) {
		super(account);
	}
}
