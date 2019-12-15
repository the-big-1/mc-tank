package company18.mctank.factory;

import company18.mctank.domain.Discount;

/**
 * Factory for {@link Discount}
 *
 * @author ArtemSer
 */
public class DiscountFactory {

	public enum DiscountType {
		REGISTRATION, // Registration 5% discount
		MC_FIVE, // Simple 5% discount after one simple purchase
		MC_TEN, // 10% discount
		HUGE, // huge 15% discount
		LEGENDARY // legendary 20% discount
	}

	/**
	 * Create discount from {@link DiscountFactory.DiscountType}.
	 *
	 * @param type discount type
	 * @return Discount
	 */
	public static Discount create(DiscountType type) {
		switch (type) {
			case REGISTRATION:
				return new Discount("Registration Bonus", 0.05f);
			case MC_FIVE:
				return new Discount("McFive", 0.05f);
			case MC_TEN:
				return new Discount("McTen", 0.1f);
			case HUGE:
				return new Discount("Huge discount", 0.15f);
			case LEGENDARY:
				return new Discount("Legendary", 0.2f);
			default:
				return null;
		}
	}

	/**
	 * Create discount from {@link DiscountFactory.DiscountType}.
	 *
	 * @param mcPoints points from your order
	 * @return Discount
	 */
	public static Discount create(int mcPoints) {
		if (mcPoints <=0 || mcPoints > 4)
			throw new IllegalArgumentException("McPoints can not be less or equal than 0 and more then 4");
		return create(DiscountType.values()[mcPoints]);
	}

}