package evl;

import java.lang.reflect.Method;

public class MethodItem<Data> {

	private Class<?>[] tuple;
	private int[] distance;
	private Method method;
	private Data data;
	
	MethodItem(Class<?>[] tuple, int[] distance, Method method, Data data) {
		this.tuple = tuple;
		this.distance = distance;
		this.method = method;
		this.data = data;
	}
	
	public Class<?>[] tuple() {
		return tuple;
	}
	
	public int[] distance() {
		return distance;
	}

	public Method method() {
		return method;
	}
	
	public Data data() {
		return data;
	}

}
