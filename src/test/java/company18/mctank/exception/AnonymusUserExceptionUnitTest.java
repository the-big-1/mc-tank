package company18.mctank.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnonymusUserExceptionUnitTest {

	@Test
	void testException(){
		AnonymusUserException testException = new AnonymusUserException();

		assertEquals(testException.getClass(), AnonymusUserException.class);
		assertEquals(testException.getMessage(), "User is not Authorized");
	}

}