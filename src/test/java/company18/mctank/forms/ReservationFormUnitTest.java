package company18.mctank.forms;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFormUnitTest {

	public ReservationForm testForm = new ReservationForm("Test", "2021/01/13 - 05:27 pm", "McSit", "TestUser");

    @Test
    void getDate() {
    	LocalDate date = LocalDate.of(2021,01,13);

    	assertEquals(testForm.getDate(), date);
    }

    @Test
    void setDate() {
		LocalDate oldDate = LocalDate.of(2021,01,13);
    	LocalDate newDate = LocalDate.of(2021,02,02);
    	testForm.setDate(newDate);

    	assertEquals(testForm.getDate(), newDate);

    	testForm.setDate(oldDate);
    }

    @Test
    void getTime() {
    }

    @Test
    void setTime() {
    	LocalTime oldTime = LocalTime.of(17, 27);
		LocalTime newTime = LocalTime.of(10,30);
		testForm.setTime(newTime);

		assertEquals(testForm.getTime(), newTime);

		testForm.setTime(oldTime);

	}

    @Test
    void getName() {
    	assertEquals(testForm.getName(), "Test");
    }

    @Test
    void setName() {
    	testForm.setName("New");
    	assertEquals(testForm.getName(), "New");

		testForm.setName("Test");

	}

    @Test
    void getMcPoint() {
    	assertEquals(testForm.getMcPoint(), "McSit");
    }

    @Test
    void setMcPoint() {
    	testForm.setMcPoint("McWash");
		assertEquals(testForm.getMcPoint(), "McWash");

		testForm.setMcPoint("McSit");

	}

    @Test
    void getUsername() {
    	assertEquals(testForm.getUsername(), "TestUser");
    }

    @Test
    void setUsername() {
    	testForm.setUsername("new");

    	assertEquals(testForm.getUsername(), "new");

    	testForm.setUsername("TestUser");
    }
}