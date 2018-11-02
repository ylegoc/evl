package org.bitbucket.evl;


public abstract class MethodComparatorD<Data> {

	private Object[] args;
	
	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}
	
	public abstract int compare(MethodItemD<Data> m1, MethodItemD<Data> m2);
	
}
