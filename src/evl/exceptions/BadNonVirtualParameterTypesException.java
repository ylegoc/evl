package evl.exceptions;

public class BadNonVirtualParameterTypesException extends RuntimeException {

	private static final long serialVersionUID = 3L;

	public BadNonVirtualParameterTypesException() {
		super("Bad non virtual parameter types");
	}
}
