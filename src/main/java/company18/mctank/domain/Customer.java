package company18.mctank.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.salespointframework.useraccount.UserAccount;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

@Entity
public class Customer{
	@OneToOne
	private UserAccount userAccount;
	private @Id @GeneratedValue long id;

	private Date registrationDate;

	@SuppressWarnings("unused")
	private Customer() {}

	public Customer(UserAccount useraccount){
		this.userAccount=useraccount;
		this.registrationDate = new Date();
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}
	public String getEmail() {
		return this.userAccount.getEmail();
	}

	public long getId() {
		return id;
	}

	public String getRegisterDate() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Berlin"));
		cal.setTime(this.registrationDate);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		return day + " / " + month + " / " + year;
	}

}