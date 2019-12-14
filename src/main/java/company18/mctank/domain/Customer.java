package company18.mctank.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.salespointframework.useraccount.Password;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.security.core.userdetails.User;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Entity
public class Customer {
	@OneToOne
	private UserAccount userAccount;
	private @Id
	@GeneratedValue
	long id;

	private String mobile;

	private Date registrationDate;

	@SuppressWarnings("unused")
	private Customer() {
	}

	public Customer(UserAccount useraccount) {
		this.userAccount = useraccount;
		this.registrationDate = new Date();
		this.userAccount.setFirstname("No Info");
		this.userAccount.setLastname("No Info");
		this.setMobile("Mobile number");
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public String getUsername() {
		return userAccount.getUsername();
	}

	public String getFullName() {
		StringBuilder fullname = new StringBuilder();
		if (!this.getFirstname().equals("No Info")) {
			fullname.append(this.getFirstname()).append(" ");
		}
		if (!this.getLastname().equals("No Info")) {
			fullname.append(this.getLastname());
		}
		return fullname.length() == 0 ? "No Info" : fullname.toString();
	}

	public String getFirstname() {
		return this.userAccount.getFirstname();
	}

	public String getLastname() {
		return this.userAccount.getLastname();
	}

	public String getEmail() {
		return this.userAccount.getEmail();
	}

	public String getMobile() {
		return this.mobile;
	}

	public long getId() {
		return this.id;
	}

	public void setFirstName(String firstName) {
		this.userAccount.setFirstname(firstName);
	}

	public void setLastName(String lastName) {
		this.userAccount.setLastname(lastName);
	}

	public void setEmail(String email) {
		this.userAccount.setEmail(email);
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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