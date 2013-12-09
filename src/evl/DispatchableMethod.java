package evl;

import java.lang.reflect.Method;

public class DispatchableMethod<DataType> {

	private Method method;
	private Object object;
	private ClassTuple tuple;
	private DataType data;
	
	public DispatchableMethod(ClassTuple tuple, Method method, Object object) {
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
	
	public ClassTuple getClassTuple() {
		return tuple;
	}

	public void setData(DataType data) {
		this.data = data;
	}
	
	public DataType getData() {
		return data;
	}
	
}
