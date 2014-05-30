package org.bitbucket.evl.exceptions;

public class BadNumberOfVirtualParameterTypesException extends RuntimeException {

	private static final long serialVersionUID = 2L;
	
	public BadNumberOfVirtualParameterTypesException() {
		super("Bad number of virtual parameter types");
	}
}
