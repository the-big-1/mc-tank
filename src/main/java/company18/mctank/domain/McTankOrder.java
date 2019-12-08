package company18.mctank.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.salespointframework.order.Order;
import org.salespointframework.useraccount.UserAccount;

@Entity
public class McTankOrder extends Order{
	
	private LocalDateTime orderdate;
	
	public McTankOrder(UserAccount account) {
		super(account);
	}
	
	public LocalDateTime getDate() {
		return this.orderdate;
	}

}
