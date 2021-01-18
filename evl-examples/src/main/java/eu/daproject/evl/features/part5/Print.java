package eu.daproject.evl.features.part5;

import eu.daproject.evl.Method1;

public class Print {

	private static Method1<String> method = new Method1<String>();
	
	public static Method1<String> method() {
		return method;
	}
	
}
