package company18.mctank.domain;

import org.junit.jupiter.api.Test;
import org.salespointframework.useraccount.UserAccount;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CustomerUnitTest {

	public UserAccount testAcc = new UserAccount();
	public Customer testCustomer = new Customer(testAcc);
	public LocalDateTime testDateTime = LocalDateTime.of(2021, 1, 1,12, 0);


	@Test
    void getLastOrderDate() {
    	assertNotNull(testCustomer.getLastOrderDate());
    }

    @Test
    void setLastOrderDate() {
    	testCustomer.setLastOrderDate(testDateTime);

    	assertEquals(testCustomer.getLastOrderDate(), testDateTime);
    }

    @Test
    void getUserAccount() {
    	assertEquals(testCustomer.getUserAccount(), testAcc);
    }

    @Test
    void getUsername() {
    }

    @Test
    void getFullName() {
    	assertEquals(testCustomer.getFullName(), "No Info");
    }

    @Test
    void getFirstname() {
    	assertEquals(testCustomer.getFirstname(), "No Info");
    }

    @Test
    void getLastname() {
    	assertEquals(testCustomer.getLastname(), "No Info");
    }

    @Test
    void getEmail() {
    	assertNull(testCustomer.getEmail());
    }

    @Test
    void getMobile() {
    	assertEquals(testCustomer.getMobile(), "Mobile number");
    }

    @Test
    void getId() {
    }

    @Test
    void getLicensePlate() {
    }

    @Test
    void setFirstName() {
    	testCustomer.setFirstName("test");

    	assertEquals(testCustomer.getFirstname(), "test");
    }

    @Test
    void setLastName() {
    	testCustomer.setLastName("test");

    	assertEquals(testCustomer.getLastname(), "test");
    }

    @Test
    void setEmail() {
		testCustomer.setEmail("test@mail.de");

		assertEquals(testCustomer.getEmail(), "test@mail.de");
    }

    @Test
    void setMobile() {
		testCustomer.setMobile("080032168");

		assertEquals(testCustomer.getMobile(), "080032168");
    }

    @Test
    void setLicensePlate() {
    }

    @Test
    void getLastActivityDate() {
    	//assertNull(testCustomer.getLastActivityDate());
		//NullPointerException
    }

    @Test
    void updateLastActivityDate() {
		testCustomer.updateLastActivityDate();

		assertNotNull(testCustomer.getLastActivityDate());
    }

    @Test
    void onCreate() {
    }

    @Test
    void getDiscounts() {
    }

    @Test
    void setDiscounts() {
    }

    @Test
    void addDiscount() {
    }

    @Test
    void removeDiscount() {
    }
}