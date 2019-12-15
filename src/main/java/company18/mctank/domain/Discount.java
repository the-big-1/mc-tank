package company18.mctank.domain;

import org.javamoney.moneta.function.MonetaryOperators;

import javax.money.MonetaryAmount;
import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Discount {

	public static final int VALID_DISCOUNT_LENGTH = 6;

	enum DiscountStatus {
		AVAILABLE, USED, EXPIRED
	}

	public Discount () {}

	public Discount(String name, float discount) {
		this.setName(name);
		this.setDiscount(discount);
		this.setStatus(DiscountStatus.AVAILABLE);
	}

	@Id
	@GeneratedValue
	private UUID id;

	private String name;
	private float discount; // can not be more than 0.2
	private DiscountStatus status;
	private Date created;

	@PrePersist
	protected void onCreate() {
		created = new Date();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Discount discount = (Discount) o;
		return Objects.equals(id, discount.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public String getShortId() {
		return this.getId().toString()
				.substring(0, VALID_DISCOUNT_LENGTH);
	}

	public String getDiscountProductName() {
		return this.getShortId()
				.concat(" | ")
				.concat(this.getName());
	}

	public MonetaryAmount getDiscountPrice(MonetaryAmount price) {
		return price
				.multiply(this.getDiscount())
				.negate()
				.with(MonetaryOperators.rounding());
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		if (discount > 0.2f)
			throw new IllegalArgumentException("discount can not be more than 20%");
		this.discount = discount;
	}

	public DiscountStatus getStatus() {
		return status;
	}

	public void setStatus(DiscountStatus status) {
		this.status = status;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}
