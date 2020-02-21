package eu.daproject.evl.features.part5;

import eu.daproject.evl.Method1;

public class Foo {

	public static Method1<Integer> method = new Method1<Integer>();
	
	public static Method1<Integer> method() {
		return method;
	}
}
