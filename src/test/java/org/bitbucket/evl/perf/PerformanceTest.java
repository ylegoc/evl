package org.bitbucket.evl.perf;

import java.util.Date;
import java.util.HashMap;

import org.bitbucket.evl.AsymmetricComparator;
import org.bitbucket.evl.DispatchableMethodD;
import org.bitbucket.evl.Method1;
import org.bitbucket.evl.Method2;
import org.bitbucket.evl.Method3;
import org.bitbucket.evl.exception.InvocationException;
import org.bitbucket.evl.util.Parameter;
import org.junit.Test;

public class PerformanceTest {

	static int N = 1 << 24;
	static int[] indexes;
	static Base[] objects = new Base[8];
	
	static void init() {
		indexes = new int[N];
		
		for (int i = 0; i < N; i++) {
			indexes[i] = (int) (1 + (Math.random() * 7));
		}
		
		objects[0] = new A1();
		objects[1] = new A2();
		objects[2] = new A3();
		objects[3] = new A4();
		objects[4] = new A5();
		objects[5] = new A6();
		objects[6] = new A7();
		objects[7] = new A8();
	}
	
	static void testMethod() {
		Date begin = new Date();
		
		int res = 0;
		for (int i = 0; i < N; i++) {
			res += objects[indexes[i]].foo();
		}
		
		Date end = new Date();
		
		System.out.println("method in " + (end.getTime() - begin.getTime()) + "ms result " + res);
	}
	
	private static void testInstanceOf() {

		Foo foo = new Foo();
		
		Date begin = new Date();
		
		int res = 0;
		for (int i = 0; i < N; i++) {
			res += foo.processAll(objects[indexes[i]]);
		}
		
		Date end = new Date();
		
		System.out.println("instanceof in " + (end.getTime() - begin.getTime()) + "ms result " + res);
	}
	
	private static void testMap() {
		
		Foo foo = new Foo();
		
		Date begin = new Date();
		
		int res = 0;
		for (int i = 0; i < N; i++) {
			res += foo.processAllMap(objects[indexes[i]]);
		}
		
		Date end = new Date();
		
		System.out.println("map in " + (end.getTime() - begin.getTime()) + "ms result " + res);
	}
	
	private static void testMethodReflect() {
		
		Foo foo = new Foo();
		
		Date begin = new Date();
		
		int res = 0;
		for (int i = 0; i < N; i++) {
			res += foo.processAllMethod(objects[indexes[i]]);
		}
		
		Date end = new Date();
		
		System.out.println("method reflect in " + (end.getTime() - begin.getTime()) + "ms result " + res);
	}
	
	private static void testMethodHandle() {
		
		Foo foo = new Foo();
		
		Date begin = new Date();
		
		int res = 0;
		for (int i = 0; i < N; i++) {
			res += foo.processAllHandle(objects[indexes[i]]);
		}
		
		Date end = new Date();
		
		System.out.println("method handle in " + (end.getTime() - begin.getTime()) + "ms result " + res);
	}
	
	static void testMultiMethod(Method1<Integer> m) {
	
		Foo foo = new Foo();
		
		try {
			m.add(Foo.class, "processA1", Parameter.types(A1.class), foo);
			m.add(Foo.class, "processA2", Parameter.types(A2.class), foo);
			m.add(Foo.class, "processA3", Parameter.types(A3.class), foo);
			m.add(Foo.class, "processA4", Parameter.types(A4.class), foo);
			m.add(Foo.class, "processA5", Parameter.types(A5.class), foo);
			m.add(Foo.class, "processA6", Parameter.types(A6.class), foo);
			m.add(Foo.class, "processA7", Parameter.types(A7.class), foo);
			m.add(Foo.class, "processA8", Parameter.types(A8.class), foo);
			
			for (int i = 0; i < 8; i++) {
				m.invoke(objects[i]);
			}
			
			Date begin = new Date();
			
			int res = 0;
			for (int i = 0; i < N; i++) {
				res += m.invoke(objects[indexes[i]]);
			}
			
			Date end = new Date();
			
			System.out.println("method 1 in " + (end.getTime() - begin.getTime()) + "ms result " + res);
			
		} catch (InvocationException e) {
			e.printStackTrace();
		}
	}
	
	static void testMultiMethod2(Method2<Integer> m) {
		
		Foo foo = new Foo();
		
		try {
			m.add(Foo.class, "processA1A1", Parameter.types(A1.class, A1.class), foo);
			m.add(Foo.class, "processA2A2", Parameter.types(A2.class, A2.class), foo);
			m.add(Foo.class, "processA3A3", Parameter.types(A3.class, A3.class), foo);
			m.add(Foo.class, "processA4A4", Parameter.types(A4.class, A4.class), foo);
			m.add(Foo.class, "processA5A5", Parameter.types(A5.class, A5.class), foo);
			m.add(Foo.class, "processA6A6", Parameter.types(A6.class, A6.class), foo);
			m.add(Foo.class, "processA7A7", Parameter.types(A7.class, A7.class), foo);
			m.add(Foo.class, "processA8A8", Parameter.types(A8.class, A8.class), foo);
			
			for (int i = 0; i < 8; i++) {
				m.invoke(objects[i], objects[i]);
			}
			
			Date begin = new Date();
			
			int res = 0;
			for (int i = 0; i < N; i++) {
				Base obj = objects[indexes[i]];
				res += m.invoke(obj, obj);
			}
			
			Date end = new Date();
			
			System.out.println("method 2 in " + (end.getTime() - begin.getTime()) + "ms result " + res);
			
		} catch (InvocationException e) {
			e.printStackTrace();
		}
	}
	
	static void testMultiMethod3(Method3<Integer> m) {

		Foo foo = new Foo();
		
		try {
			m.add(Foo.class, "processA1A1A1", Parameter.types(A1.class, A1.class, A1.class), foo);
			m.add(Foo.class, "processA2A2A2", Parameter.types(A2.class, A2.class, A2.class), foo);
			m.add(Foo.class, "processA3A3A3", Parameter.types(A3.class, A3.class, A3.class), foo);
			m.add(Foo.class, "processA4A4A4", Parameter.types(A4.class, A4.class, A4.class), foo);
			m.add(Foo.class, "processA5A5A5", Parameter.types(A5.class, A5.class, A5.class), foo);
			m.add(Foo.class, "processA6A6A6", Parameter.types(A6.class, A6.class, A6.class), foo);
			m.add(Foo.class, "processA7A7A7", Parameter.types(A7.class, A7.class, A7.class), foo);
			m.add(Foo.class, "processA8A8A8", Parameter.types(A8.class, A8.class, A8.class), foo);
			
			for (int i = 0; i < 8; i++) {
				m.invoke(objects[i], objects[i], objects[i]);
			}
			
			Date begin = new Date();
			
			int res = 0;
			for (int i = 0; i < N; i++) {
				Base obj = objects[indexes[i]];
				res += m.invoke(obj, obj, obj);
			}
			
			Date end = new Date();
			
			System.out.println("method 3 in " + (end.getTime() - begin.getTime()) + "ms result " + res);
			
		} catch (InvocationException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAll() {
		
		init();
		testMethod();
		testInstanceOf();
		testMap();
		testMethodReflect();
		testMethodHandle();
		
		testMultiMethod(new Method1<Integer>()
								.comparator(new AsymmetricComparator()));
		
		testMultiMethod2(new Method2<Integer>()
								.comparator(new AsymmetricComparator()));
		
		testMultiMethod3(new Method3<Integer>()
								.comparator(new AsymmetricComparator()));
		
		testMultiMethod(new Method1<Integer>()
								.comparator(new AsymmetricComparator())
								.cache(new HashMap<Class<?>, DispatchableMethodD<Void>>()));
		
		testMultiMethod(new Method1<Integer>()
				.comparator(new AsymmetricComparator())
				.boundedCache(32));
		
		testMultiMethod(new Method1<Integer>()
				.comparator(new AsymmetricComparator())
				.boundedCache(8));
		
		testMultiMethod(new Method1<Integer>()
				.comparator(new AsymmetricComparator())
				.boundedCache(7));
		
		testMultiMethod(new Method1<Integer>()
				.comparator(new AsymmetricComparator())
				.boundedCache(6));
		
		testMultiMethod(new Method1<Integer>()
				.comparator(new AsymmetricComparator())
				.boundedCache(5));
		
		testMultiMethod(new Method1<Integer>()
				.comparator(new AsymmetricComparator())
				.boundedCache(4));
	}

}
