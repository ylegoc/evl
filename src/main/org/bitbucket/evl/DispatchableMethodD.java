package org.bitbucket.evl;

import java.lang.reflect.Method;

import org.bitbucket.evl.util.MethodClassTuple;

public class DispatchableMethodD<DataType> {

	private Method method;
	private Object object;
	private MethodClassTuple tuple;
	private DataType data;
	
	public DispatchableMethodD(MethodClassTuple tuple, Method method, Object object) {
		this.method = method;
		this.object = object;
		this.tuple = tuple;
	}
	
	public Method getMethod() {
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
