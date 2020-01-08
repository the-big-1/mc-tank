package company18.mctank.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class McSitReservationUnitTest {

	LocalDateTime testTime = LocalDateTime.now();
	McSitReservation reservationTest = new McSitReservation("test", testTime,"testUser");


	@Test
	void ConstructorTest(){

		assertEquals(reservationTest.getClass(), McSitReservation.class);
		assertEquals(reservationTest.getName(), "test");
		assertEquals(reservationTest.getUsername(),"testUser");
	}

    @Test
    void getMcPoint() {
		assertEquals(reservationTest.getMcPoint(), "McSit");
    }
}