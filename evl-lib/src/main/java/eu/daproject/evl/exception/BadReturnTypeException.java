package eu.daproject.evl.exception;

public class BadReturnTypeException extends RuntimeException {

	private static final long serialVersionUID = 4L;

	/**
	 * Constructs an exception with predefined message.
	 */
	public BadReturnTypeException() {
		super("Bad return type");
	}
}