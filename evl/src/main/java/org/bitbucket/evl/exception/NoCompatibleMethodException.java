package org.bitbucket.evl.exception;

import org.bitbucket.evl.util.ClassTuple;

public class NoCompatibleMethodException extends InvocationException {

	private static final long serialVersionUID = 7L;
	
	public NoCompatibleMethodException(ClassTuple tuple) {
		super("No compatible method for " + tuple);
	}
}
