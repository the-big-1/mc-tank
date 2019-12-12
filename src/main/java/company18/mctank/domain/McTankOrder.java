package company18.mctank.domain;


import javax.persistence.Entity;

import org.salespointframework.order.Order;
import org.salespointframework.useraccount.UserAccount;

@Entity
public class McTankOrder extends Order implements Comparable<McTankOrder>{
	
	@SuppressWarnings("unused")
	private McTankOrder() {};
	
	public McTankOrder(UserAccount account) {
		super(account);
	}

	
	// sorting from latest to earliest
	@Override
	public int compareTo(McTankOrder o) {
		if (this.getDateCreated().isAfter(o.getDateCreated()))
			return -1;
		else if (o.getDateCreated().isAfter(this.getDateCreated()))
			return 1;
		else return 0;
	}
}
