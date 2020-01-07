package company18.mctank.forms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LicensePlateFormUnitTest {

	public LicensePlateForm testForm = new LicensePlateForm("DD-Q-1001", 80001010l);

    @Test
    void getLicensePlate() {
    	assertEquals(testForm.getLicensePlate(), "DD-Q-1001");
    }

    @Test
    void getId() {
    	assertEquals(testForm.getId(), 80001010l);
    }
}