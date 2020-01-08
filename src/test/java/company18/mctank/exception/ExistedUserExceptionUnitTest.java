package company18.mctank.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExistedUserExceptionUnitTest {

	@Test
	void testException(){
		ExistedUserException testException = new ExistedUserException();

		assertEquals(testException.getClass(), ExistedUserException.class);
		assertEquals(testException.getMessage(), "User with name test already exists!");
	}

}