package eu.daproject.evl.examples.printer;

import eu.daproject.evl.Method2;

public class Print {

	private static Method2<String> method = new Method2<String>()
													.returnType(String.class)
													.nonVirtualParameterTypes();
	
	public static Method2<String> method() {
		return method;
	}
	
}
