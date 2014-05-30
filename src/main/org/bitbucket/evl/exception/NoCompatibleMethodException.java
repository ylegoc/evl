package org.bitbucket.evl.exception;

import org.bitbucket.evl.util.MethodClassTuple;

public class NoCompatibleMethodException extends InvocationException {

	private static final long serialVersionUID = 7L;
	
	public NoCompatibleMethodException(MethodClassTuple tuple) {
		super("No compatible method for " + tuple);
	}
}
