package evl.exceptions;

public class MethodComparatorInstantiationException extends InvocationException {

	private static final long serialVersionUID = 4L;
	
	public MethodComparatorInstantiationException() {
		super("Cannot instantiate method comparator");
	}
}
