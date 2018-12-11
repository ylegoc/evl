package org.bitbucket.evl;

import java.lang.invoke.MethodHandle;

import org.bitbucket.evl.util.ClassTuple;

class MatchMethod {

	private MethodHandle methodHandle;
	private Object object;
	private ClassTuple tuple;
	private Comparable<?> data;
	private boolean lastAdded = false;
	
	MatchMethod(ClassTuple tuple, MethodHandle method, Object object) {
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
	
	public ClassTuple getClassTuple() {
		return tuple;
	}

	public void setData(Comparable<?> data) {
		this.data = data;
	}
	
	public Comparable<?> getData() {
		return data;
	}
	
}
