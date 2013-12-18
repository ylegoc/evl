package tests.perfs;

import java.util.AbstractMap;
import java.util.HashMap;

public class Foo {

	private AbstractMap<Class<?>, Integer> processMap = new HashMap<Class<?>, Integer>();
	
	public Foo() {
		processMap.put(A1.class, 1);
		processMap.put(A2.class, 2);
		processMap.put(A3.class, 3);
		processMap.put(A4.class, 4);
		processMap.put(A5.class, 5);
		processMap.put(A6.class, 6);
		processMap.put(A7.class, 7);
		processMap.put(A8.class, 8);
	}
	
	public int processA1(A1 a) {
		return 1;
	}
	
	public int processA2(A2 a) {
		return 2;
	}
	
	public int processA3(A3 a) {
		return 3;
	}
	
	public int processA4(A4 a) {
		return 4;
	}
	
	public int processA5(A5 a) {
		return 5;
	}
	
	public int processA6(A6 a) {
		return 6;
	}
	
	public int processA7(A7 a) {
		return 7;
	}
	
	public int processA8(A8 a) {
		return 8;
	}
	
	public int processAll(IA a) {
		if (a instanceof A1) {
			return processA1((A1)a);
		}
		if (a instanceof A2) {
			return processA2((A2)a);
		}
		if (a instanceof A3) {
			return processA3((A3)a);
		}
		if (a instanceof A4) {
			return processA4((A4)a);
		}
		if (a instanceof A5) {
			return processA5((A5)a);
		}
		if (a instanceof A6) {
			return processA6((A6)a);
		}
		if (a instanceof A7) {
			return processA7((A7)a);
		}
		if (a instanceof A8) {
			return processA8((A8)a);
		}
		
		return 0;
	}
	
	public int processAllMap(IA a) {
		return processMap.get(a.getClass());
	}
}
