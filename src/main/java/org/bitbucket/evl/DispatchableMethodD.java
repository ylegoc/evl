package org.bitbucket.evl;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;

import org.bitbucket.evl.util.MethodClassTuple;

public class DispatchableMethodD<DataType> {

	private MethodHandle method;
	private Object object;
	private MethodClassTuple tuple;
	private DataType data;
	
	public DispatchableMethodD(MethodClassTuple tuple, MethodHandle method, Object object) {
		this.method = method;
		this.object = object;
		this.tuple = tuple;
	}
	
	public MethodHandle getMethod() {
		return method;
	}
	
	public Object getObject() {
		return object;
	}
	
	public MethodClassTuple getClassTuple() {
		return tuple;
	}

	public void setData(DataType data) {
		this.data = data;
	}
	
	public DataType getData() {
		return data;
	}
	
}
