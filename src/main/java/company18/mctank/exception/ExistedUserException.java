package company18.mctank.exception;

public class ExistedUserException extends Exception {
	public ExistedUserException() {
		this("User with name test already exists!");
	}

	public ExistedUserException(String message) {
		super(message);
	}
}
