package org.bitbucket.evl;

import java.lang.invoke.MethodHandle;

import org.bitbucket.evl.util.MethodClassTuple;

class MatchMethod {

	private MethodHandle methodHandle;
	private Object object;
	private MethodClassTuple tuple;
	private Comparable<?> data;
	private boolean lastAdded = false;
	
	MatchMethod(MethodClassTuple tuple, MethodHandle method, Object object) {
		this.methodHandle = method;
		this.object = object;
		this.tuple = tuple;
	}
	
	void setLastAdded(boolean value) {
		lastAdded = value;
	}
	
	boolean isLastAdded() {
		return lastAdded;
	}
	
	public MethodHandle getMethod() {
		return methodHandle;
	}
	
	public Object getObject() {
		return object;
	}
	
	public MethodClassTuple getClassTuple() {
		return tuple;
	}

	public void setData(Comparable<?> data) {
		this.data = data;
	}
	
	public Comparable<?> getData() {
		return data;
	}
	
}
