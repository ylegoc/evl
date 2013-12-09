package tests;

import java.util.HashMap;

import tests.classes.E;
import evl.util.SuperClass;

public class Test {

	public static void main(String[] args) {
	
		HashMap<Class<?>, Integer> classDistanceMap = SuperClass.calculate(E.class);
		
		System.out.println(classDistanceMap);
	}
}
