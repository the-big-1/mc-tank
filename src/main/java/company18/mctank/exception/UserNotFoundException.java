package company18.mctank.exception;

public class UserNotFoundException extends Exception {
	public UserNotFoundException() {
		this("User was not found in DB");
	}

	public UserNotFoundException(String message) {
		super(message);
	}
}
