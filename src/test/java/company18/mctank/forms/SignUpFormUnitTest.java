package company18.mctank.forms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignUpFormUnitTest {


	public SignUpForm testForm = new SignUpForm("test", "test@mail.de", "123", "CUSTOMER");

    @Test
    void getPassword() {
    	assertEquals(testForm.getPassword(), "123");
    }

    @Test
    void getLicensePlate() {
    	assertEquals(testForm.getLicensePlate(), "test");
    }

    @Test
    void getEmail() {
    	assertEquals(testForm.getEmail(), "test@mail.de");
    }
}