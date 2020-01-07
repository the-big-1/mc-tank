package company18.mctank.domain;

import company18.mctank.factory.DiscountFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DiscountUnitTest {

	public Discount testDiscount = new Discount("test", 0.05f);

	@BeforeAll
	void init(){
		testDiscount.setId(UUID.randomUUID());
	}

    @Test
    void onCreate() {
    	//assertNotNull(testDiscount.getCreated());
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {

    }

    @Test
    void getShortId() {
    	assertNotNull(testDiscount.getShortId());
    }

    @Test
    void getDiscountProductName() {
    	assertEquals(testDiscount.getDiscountProductName(), testDiscount.getShortId() + " | " + "test");
    }

    @Test
    void getDiscountPrice() {
		assertNotNull(testDiscount.getDiscountPrice(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(10).create()));
    }

    @Test
    void getId() {
    	assertNotNull(testDiscount.getId());
    }

    @Test
    void setId() {
    	testDiscount.setId(UUID.randomUUID());
    	assertNotNull(testDiscount.getId());
    }

    @Test
    void getName() {
    	assertEquals(testDiscount.getName(), "test");
    }

    @Test
    void setName() {
    	testDiscount.setName("new");

    	assertEquals(testDiscount.getName(), "new");
    }

    @Test
    void getDiscount() {
    	assertEquals(testDiscount.getDiscount(), 0.05f);
    }

    @Test
    void setDiscount() {
		try {
			testDiscount.setDiscount(0.3f);
		}
		catch (IllegalArgumentException e){
			assertNotNull(e);
		}
    }

    @Test
    void getStatus() {
    	assertEquals(testDiscount.getStatus(), Discount.DiscountStatus.AVAILABLE);
    }

    @Test
    void setStatus() {
		testDiscount.setStatus(Discount.DiscountStatus.EXPIRED);

		assertEquals(testDiscount.getStatus(), Discount.DiscountStatus.EXPIRED);

		testDiscount.setStatus(Discount.DiscountStatus.AVAILABLE);
    }

    @Test
    void getCreated() {
    	//assertNotNull(testDiscount.getCreated());
    }

    @Test
    void setCreated() {
		testDiscount.setCreated(new Date());

		assertNotNull(testDiscount.getCreated());
    }
}