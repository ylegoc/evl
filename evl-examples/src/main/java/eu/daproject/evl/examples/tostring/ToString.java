package eu.daproject.evl.examples.tostring;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method2;

public class ToString {

	private static Method2<String> method = new Method2<String>()
													.returnType(String.class)
													.nonVirtualParameterTypes();
	
	public static Method2<String> method() {
		return method;
	}
	
	static {
		method.add(new Cases() {
			String match(Object format, Object object) {
				return object.toString();
			}
		});
	}
	
}
