package company18.mctank.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserNotFoundExceptionUnitTest {

	@Test
	void testException(){
		UserNotFoundException testException = new UserNotFoundException();

		assertEquals(testException.getClass(), UserNotFoundException.class);
		assertEquals(testException.getMessage(), "User was not found in DB");
	}

}