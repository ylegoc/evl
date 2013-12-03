package evl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class DispatchableMethod<ReturnType, DataType> {

	private Method method;
	private Object object;
	private ClassTuple tuple;
	private DataType data;
	
	public DispatchableMethod(ClassTuple tuple, Method method, Object object) {
		this.method = method;
		this.object = object;
		this.tuple = tuple;
	}
	
	public ClassTuple tuple() {
		return tuple;
	}

	public void setData(DataType data) {
		this.data = data;
	}
	
	public DataType data() {
		return data;
	}
	
	@SuppressWarnings("unchecked")
	public ReturnType invoke(Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return (ReturnType)method.invoke(object, args);
	}

}
