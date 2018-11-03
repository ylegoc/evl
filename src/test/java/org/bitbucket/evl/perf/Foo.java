package org.bitbucket.evl.perf;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Foo {

	private Map<Class<?>, Integer> processMap = new HashMap<Class<?>, Integer>();
	private Map<Class<?>, Method> methodMap = new HashMap<Class<?>, Method>();
	private Map<Class<?>, MethodHandle> handleMap = new HashMap<Class<?>, MethodHandle>();
	
	public Foo() {
		processMap.put(A1.class, 1);
		processMap.put(A2.class, 2);
		processMap.put(A3.class, 3);
		processMap.put(A4.class, 4);
		processMap.put(A5.class, 5);
		processMap.put(A6.class, 6);
		processMap.put(A7.class, 7);
		processMap.put(A8.class, 8);
		
		
		try {
			methodMap.put(A1.class, Foo.class.getMethod("processA1", A1.class));
			methodMap.put(A2.class, Foo.class.getMethod("processA2", A2.class));
			methodMap.put(A3.class, Foo.class.getMethod("processA3", A3.class));
			methodMap.put(A4.class, Foo.class.getMethod("processA4", A4.class));
			methodMap.put(A5.class, Foo.class.getMethod("processA5", A5.class));
			methodMap.put(A6.class, Foo.class.getMethod("processA6", A6.class));
			methodMap.put(A7.class, Foo.class.getMethod("processA7", A7.class));
			methodMap.put(A8.class, Foo.class.getMethod("processA8", A8.class));
			
			for (Method m : methodMap.values()) {
				m.setAccessible(true);
			}
			
		} catch (NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
		}
		
		
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		
		try {
			handleMap.put(A1.class, lookup.findVirtual(Foo.class, "processA1", MethodType.methodType(int.class, A1.class)));
			handleMap.put(A2.class, lookup.findVirtual(Foo.class, "processA2", MethodType.methodType(int.class, A2.class)));
			handleMap.put(A3.class, lookup.findVirtual(Foo.class, "processA3", MethodType.methodType(int.class, A3.class)));
			handleMap.put(A4.class, lookup.findVirtual(Foo.class, "processA4", MethodType.methodType(int.class, A4.class)));
			handleMap.put(A5.class, lookup.findVirtual(Foo.class, "processA5", MethodType.methodType(int.class, A5.class)));
			handleMap.put(A6.class, lookup.findVirtual(Foo.class, "processA6", MethodType.methodType(int.class, A6.class)));
			handleMap.put(A7.class, lookup.findVirtual(Foo.class, "processA7", MethodType.methodType(int.class, A7.class)));
			handleMap.put(A8.class, lookup.findVirtual(Foo.class, "processA8", MethodType.methodType(int.class, A8.class)));
			
		} catch (NoSuchMethodException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
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
	
	public int processAll(Base a) {
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
	
	public int processAllMap(Base a) {
		return processMap.get(a.getClass());
	}
	
	public int processAllMethod(Base a) {
		try {
			return (int)methodMap.get(a.getClass()).invoke(this, a);
		} catch (Throwable e) {
		}
		
		return 0;
	}
	
	public int processAllHandle(Base a) {
		try {
			return (int)handleMap.get(a.getClass()).invoke(this, a);
		} catch (Throwable e) {
		}
		
		return 0;
	}
	
	public int processA1A1(A1 a, A1 b) {
		return 1;
	}
	
	public int processA2A2(A2 a, A2 b) {
		return 2;
	}
	
	public int processA3A3(A3 a, A3 b) {
		return 3;
	}
	
	public int processA4A4(A4 a, A4 b) {
		return 4;
	}
	
	public int processA5A5(A5 a, A5 b) {
		return 5;
	}
	
	public int processA6A6(A6 a, A6 b) {
		return 6;
	}
	
	public int processA7A7(A7 a, A7 b) {
		return 7;
	}
	
	public int processA8A8(A8 a, A8 b) {
		return 8;
	}
	
	
	public int processA1A1A1(A1 a, A1 b, A1 c) {
		return 1;
	}
	
	public int processA2A2A2(A2 a, A2 b, A2 c) {
		return 2;
	}
	
	public int processA3A3A3(A3 a, A3 b, A3 c) {
		return 3;
	}
	
	public int processA4A4A4(A4 a, A4 b, A4 c) {
		return 4;
	}
	
	public int processA5A5A5(A5 a, A5 b, A5 c) {
		return 5;
	}
	
	public int processA6A6A6(A6 a, A6 b, A6 c) {
		return 6;
	}
	
	public int processA7A7A7(A7 a, A7 b, A7 c) {
		return 7;
	}
	
	public int processA8A8A8(A8 a, A8 b, A8 c) {
		return 8;
	}
	
}
