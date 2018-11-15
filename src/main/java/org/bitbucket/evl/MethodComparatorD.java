package org.bitbucket.evl;


public abstract class MethodComparatorD {

	private Object[] args;
	
	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}
	
	public abstract int compare(MethodItemD m1, MethodItemD m2);
	
}
