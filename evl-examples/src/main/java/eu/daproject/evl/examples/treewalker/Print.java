package eu.daproject.evl.examples.treewalker;

import eu.daproject.evl.Method1;

public class Print {

	private static Method1<Void> method = new Method1<Void>()
			.returnType(Void.class)
			.nonVirtualParameterTypes(Space.class);

	public static Method1<Void> method() {
		return method;
	}
	
	public static void print(Object object) throws Throwable {
		method.invoke(object, new Space());
	}
}
