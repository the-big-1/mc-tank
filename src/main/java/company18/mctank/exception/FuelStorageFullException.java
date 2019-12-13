package company18.mctank.exception;

public class FuelStorageFullException extends Exception {
	public FuelStorageFullException() {
		this("The ordered fuel amount is to large to fit into Storage");
	}

	public FuelStorageFullException(String message) {
		super(message);
	}
}
