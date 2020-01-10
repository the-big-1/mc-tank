package company18.mctank.service;

import company18.mctank.domain.Customer;
import company18.mctank.domain.CustomerRoles;
import company18.mctank.exception.ExistedUserException;
import company18.mctank.forms.CustomerInfoUpdateForm;
import company18.mctank.forms.SignUpForm;
import company18.mctank.repository.CustomerRepository;
import org.junit.After;
import org.junit.jupiter.api.*;
import org.salespointframework.useraccount.Password;
import org.salespointframework.useraccount.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceUnitTest {

	@Autowired
	private CustomerService testService;

    @Test
    void createCustomer() {
		SignUpForm testForm = new SignUpForm("unitTest","test@mail.de", "123");

		try {
			testService.createCustomer(testForm);
		}
		catch (ExistedUserException e){
			fail();
			//could fail because of database
			//@AfterAll should delete Customers
		}
    }

    @Test
    void testCreateCustomer() {
    	try {
    		testService.createCustomer("unitTest2","test2@mail.de", Password.UnencryptedPassword.of("123"), CustomerRoles.CUSTOMER);
		}
    	catch (ExistedUserException e){
    		fail();
		}

		try {
			testService.createCustomer("unitTest2","test2@mail.de", Password.UnencryptedPassword.of("123"), CustomerRoles.CUSTOMER);
		}
		catch (ExistedUserException e){
			assertEquals(e.getClass(), ExistedUserException.class);  //second call to get Exception
		}
    }

    @Test
    void disableCustomer() {
    }

    @Test
    void enableCustomer() {
    }

    @Test
    void deleteCustomer() {
    	try {
			testService.createCustomer("unitTest3", "test3@mail.de", Password.UnencryptedPassword.of("123"), CustomerRoles.CUSTOMER);
		}
    	catch (ExistedUserException e){
    		fail();
		}

		Customer delete = testService.getCustomer("unitTest3");
		long deleteId = delete.getId();
		testService.deleteCustomer(deleteId);
    }

    @Test
    void updateCustomer() {
		try {
			testService.createCustomer("unitTest4", "test4@mail.de", Password.UnencryptedPassword.of("123"), CustomerRoles.CUSTOMER);
		}
		catch (ExistedUserException e){
			fail();
		}

		Customer test = testService.getCustomer("unitTest4");
		long testId = test.getId();
    	CustomerInfoUpdateForm testform = new CustomerInfoUpdateForm("newTest","newTest","newTest@mail.de","800032168", testId);

    	testService.updateCustomer(testform);

    }

    @Test
    void updateCustomersDiscounts() {
    }

    @Test
    void updateCurrentCustomerLastActivityDate() {
    }

    @Test
    void updatePassword() {
    }

    @Test
    void findAll() {
    }

    @Test
    void isAdmin() {
    	//assertFalse(testService.isAdmin());
    }

    @Test
    void isManager() {
    	//assertFalse(testService.isManager());
    }

    @Test
    void isCustomer() {
    	//assertTrue(testService.isCustomer());
    }

    @Test
    void getCurrentCustomer() {
    }

    @Test
    void getCurrentUserAccount() {
    }

    @Test
    void getCustomer() {
    }

    @Test
    void getUserAccount() {
    }

    @Test
    void testGetCustomer() {
    }

    @Test
    void deleteLongInactiveUsers() {
    }

    @Test
    void findAllActiveUsersAmount() {
    }

    @Test
    void findAllActivePercent() {
    }

    @Test
    void updateLicensePlate() {
    }

	@AfterAll
	void delete(){
		Customer delete = testService.getCustomer("unitTest");
		long deleteId = delete.getId();
		testService.deleteCustomer(deleteId);

		Customer delete2 = testService.getCustomer("unitTest2");
		long deleteId2 = delete2.getId();
		testService.deleteCustomer(deleteId2);

		Customer delete4 = testService.getCustomer("unitTest4");
		long deleteId4 = delete4.getId();
		testService.deleteCustomer(deleteId4);
	}
}