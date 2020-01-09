package company18.mctank.service;

import company18.mctank.domain.CustomerRoles;
import company18.mctank.exception.ExistedUserException;
import company18.mctank.forms.SignUpForm;
import company18.mctank.repository.CustomerRepository;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.salespointframework.useraccount.Password;
import org.salespointframework.useraccount.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceUnitTest {
	/*

	@Autowired
	private CustomerService testService;

	@Autowired
	private CustomerRepository customerRepository;

	@BeforeAll
	void init(){
		customerRepository.deleteAll();
	}

    @Test
    void createCustomer() {
		SignUpForm testForm = new SignUpForm("unitTest2","test2@mail.de", "123");

		try {
			testService.createCustomer(testForm);
		}
		catch (ExistedUserException e){
			fail();
			//fail because of in memory database ??
		}
    }

    @Test
    void testCreateCustomer() {
    	try {
    		testService.createCustomer("unitTest","test@mail.de", Password.UnencryptedPassword.of("123"), CustomerRoles.CUSTOMER);
		}
    	catch (ExistedUserException e){
    		fail();
		}

		try {
			testService.createCustomer("unitTest","test@mail.de", Password.UnencryptedPassword.of("123"), CustomerRoles.CUSTOMER);
		}
		catch (ExistedUserException e){
			assertEquals(e.getClass(), ExistedUserException.class);
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
    }

    @Test
    void updateCustomer() {
    }

    @Test
    void testUpdateCustomer() {
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

    @AfterEach
	void delete(){
    	customerRepository.deleteAll();
	}


	 */
}