package evl.exceptions;

import evl.util.MethodClassTuple;

public class NoCompatibleMethodException extends EVLException {

	private static final long serialVersionUID = 5L;
	
	public NoCompatibleMethodException(MethodClassTuple tuple) {
		super("No compatible method for " + tuple);
	}
}
