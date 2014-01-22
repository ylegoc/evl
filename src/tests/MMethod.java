package tests;

import java.lang.reflect.Method;

public class MMethod {

	public MMethod() {
		showMethods();
	}
	
	public void showMethods() {
		Method[] methods = getClass().getDeclaredMethods();
		
		for (Method m : methods) {
			System.out.println("method " + m);
		}
	}
}
