package evl.exceptions;

import evl.util.MethodClassTuple;

public class AmbiguousMethodException extends InvocationException {

	private static final long serialVersionUID = 5L;
	
	public AmbiguousMethodException(MethodClassTuple tuple, String possibleMethods) {
		super("Ambiguity for " + tuple + ", possible methods are\n" + possibleMethods);
	}
}
