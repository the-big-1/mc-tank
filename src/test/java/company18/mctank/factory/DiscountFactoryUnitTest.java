package company18.mctank.factory;

import company18.mctank.domain.Discount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscountFactoryUnitTest {

    @Test
    void create() {
    	assertEquals(DiscountFactory.create(DiscountFactory.DiscountType.REGISTRATION).getClass(), Discount.class);
		assertEquals(DiscountFactory.create(DiscountFactory.DiscountType.MC_FIVE).getClass(), Discount.class);
		assertEquals(DiscountFactory.create(DiscountFactory.DiscountType.MC_TEN).getClass(), Discount.class);
		assertEquals(DiscountFactory.create(DiscountFactory.DiscountType.HUGE).getClass(), Discount.class);
		assertEquals(DiscountFactory.create(DiscountFactory.DiscountType.LEGENDARY).getClass(), Discount.class);

	}

    @Test
    void testCreate() {
    	assertEquals(DiscountFactory.create(1).getClass(), Discount.class);

    	try {
			DiscountFactory.create(-1);
		}
    	catch (IllegalArgumentException e){
		}

		try {
			DiscountFactory.create(5);
		}
		catch (IllegalArgumentException e){
		}
    }
}