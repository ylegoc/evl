package org.bitbucket.evl;


public abstract class MethodComparator {

	private Object[] args;
	
	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}
	
	public abstract int compare(MethodItem m1, MethodItem m2);
	
}
