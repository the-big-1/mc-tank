package company18.mctank.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class McWashReservationTest {

	LocalDateTime testTime = LocalDateTime.now();
	McWashReservation reservationTest = new McWashReservation("test",testTime,"testUser");


	@Test
	void ConstructorTest(){

		assertEquals(reservationTest.getClass(), McWashReservation.class);
		assertEquals(reservationTest.getName(), "test");
		assertEquals(reservationTest.getUsername(),"testUser");
	}

    @Test
    void getMcPoint() {
		assertEquals(reservationTest.getMcPoint(), "McWash");
    }
}