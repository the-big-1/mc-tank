package company18.mctank.exception;

public class AnonymusUserException extends Exception {
	public AnonymusUserException() {
		this("User is not Authorized");
	}

	public AnonymusUserException(String message) {
		super(message);
	}
}
