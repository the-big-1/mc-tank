package company18.mctank.domain;


import javax.persistence.*;

import org.salespointframework.useraccount.UserAccount;

import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Customer {
	@OneToOne
	private UserAccount userAccount;
	private @Id
	@GeneratedValue
	long id;

	private String mobile;

	private Date lastActivityDate;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Discount> discounts;
	private LocalDateTime lastOrderDate;

	@SuppressWarnings("unused")
	private Customer() {
	}

	public LocalDateTime getLastOrderDate() {
		return lastOrderDate;
	}

	public void setLastOrderDate(LocalDateTime lastOrderDate) {
		this.lastOrderDate = lastOrderDate;
	}

	public Customer(UserAccount useraccount) {
		this.userAccount = useraccount;
		this.lastActivityDate = new Date();
		this.userAccount.setFirstname("No Info");
		this.userAccount.setLastname("No Info");
		this.setMobile("Mobile number");
		this.lastOrderDate = LocalDateTime.now();
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

	public String getLastActivityDate() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Berlin"));
		cal.setTime(this.lastActivityDate);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		return day + " / " + month + " / " + year;
	}

	public void updateLastActivityDate(){

	}

	public List<Discount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(List<Discount> discounts) {
		this.discounts = discounts;
	}

	public void addDiscount(Discount discount) {
		if (this.discounts == null)
			this.discounts = new LinkedList<>();
		this.discounts.add(discount);
	}

	public void removeDiscount(String discountProductName) {
		final String discountShortProductName = discountProductName.substring(0, Discount.VALID_DISCOUNT_LENGTH);
		getDiscounts().stream()
				.filter(discount -> discount.getShortId().equals(discountShortProductName))
				.findFirst()
				.get()
				.setStatus(Discount.DiscountStatus.USED);
	}

}