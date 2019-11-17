package kickstart.customer;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.salespointframework.useraccount.UserAccount;

@Entity
public class Customer{
	@OneToOne
	private UserAccount userAccount;
	private @Id @GeneratedValue long id;

	@SuppressWarnings("unused")
	private Customer() {}

Customer(UserAccount useraccount){
	this.userAccount=useraccount;
}

public UserAccount getUserAccount() {
	return userAccount;
}

public long getId() {
	return id;
}

}

