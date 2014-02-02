package evl.exceptions;

import evl.MethodClassTuple;

public class AmbiguousMethodException extends EVLException {

	private static final long serialVersionUID = 5L;
	
	public AmbiguousMethodException(MethodClassTuple tuple, String possibleMethods) {
		super("Ambiguity for " + tuple + ", possible methods are\n" + possibleMethods);
	}
}
