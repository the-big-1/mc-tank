package company18.mctank.exception;

public class UnauthorizedUserException extends Exception {
	public UnauthorizedUserException() {
		this("User is not Authorized");
	}

	public UnauthorizedUserException(String message) {
		super(message);
	}
}
