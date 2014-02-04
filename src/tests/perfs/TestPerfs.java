package tests.perfs;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;

import evl.base.AsymmetricComparator;
import evl.base.Method1;
import evl.base.Method2;
import evl.base.Method3;
import evl.data.DispatchableMethodD;
import evl.data.Method2D;
import evl.data.Method3D;

public class TestPerfs {

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
	
	static void testMultiMethod(Method1<Integer> m) {
	
		Foo foo = new Foo();
		
		try {
			m.add(Foo.class.getMethod("processA1", A1.class), foo);
			m.add(Foo.class.getMethod("processA2", A2.class), foo);
			m.add(Foo.class.getMethod("processA3", A3.class), foo);
			m.add(Foo.class.getMethod("processA4", A4.class), foo);
			m.add(Foo.class.getMethod("processA5", A5.class), foo);
			m.add(Foo.class.getMethod("processA6", A6.class), foo);
			m.add(Foo.class.getMethod("processA7", A7.class), foo);
			m.add(Foo.class.getMethod("processA8", A8.class), foo);
			
			for (int i = 0; i < 8; i++) {
				m.invoke(objects[i]);
			}
			
			Date begin = new Date();
			
			int res = 0;
			for (int i = 0; i < N; i++) {
				res += m.invoke(objects[indexes[i]]);
			}
			
			Date end = new Date();
			
			System.out.println("method in " + (end.getTime() - begin.getTime()) + "ms result " + res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void testMultiMethod2(Method2<Integer> m) {
		
		Foo foo = new Foo();
		
		try {
			m.add(Foo.class.getMethod("processA1A1", A1.class, A1.class), foo);
			m.add(Foo.class.getMethod("processA2A2", A2.class, A2.class), foo);
			m.add(Foo.class.getMethod("processA3A3", A3.class, A3.class), foo);
			m.add(Foo.class.getMethod("processA4A4", A4.class, A4.class), foo);
			m.add(Foo.class.getMethod("processA5A5", A5.class, A5.class), foo);
			m.add(Foo.class.getMethod("processA6A6", A6.class, A6.class), foo);
			m.add(Foo.class.getMethod("processA7A7", A7.class, A7.class), foo);
			m.add(Foo.class.getMethod("processA8A8", A8.class, A8.class), foo);
			
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void testMultiMethod3(Method3<Integer> m) {

		Foo foo = new Foo();
		
		try {
			m.add(Foo.class.getMethod("processA1A1A1", A1.class, A1.class, A1.class), foo);
			m.add(Foo.class.getMethod("processA2A2A2", A2.class, A2.class, A2.class), foo);
			m.add(Foo.class.getMethod("processA3A3A3", A3.class, A3.class, A3.class), foo);
			m.add(Foo.class.getMethod("processA4A4A4", A4.class, A4.class, A4.class), foo);
			m.add(Foo.class.getMethod("processA5A5A5", A5.class, A5.class, A5.class), foo);
			m.add(Foo.class.getMethod("processA6A6A6", A6.class, A6.class, A6.class), foo);
			m.add(Foo.class.getMethod("processA7A7A7", A7.class, A7.class, A7.class), foo);
			m.add(Foo.class.getMethod("processA8A8A8", A8.class, A8.class, A8.class), foo);
			
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		init();
		testMethod();
		testInstanceOf();
		testMap();
		
		testMultiMethod(Method1.<Integer>builder()
								.comparator(new AsymmetricComparator()).build());
		
		testMultiMethod2(Method2.<Integer>builder().comparator(new AsymmetricComparator()).build());
		//testMultiMethod3(Method3.<Integer>builder().comparator(new AsymmetricComparator()).build());
		
		testMultiMethod(Method1.<Integer>builder()
								.comparator(new AsymmetricComparator())
								.cache(new HashMap<Class<?>, DispatchableMethodD<Void>>()).build());
		
		testMultiMethod(Method1.<Integer>builder()
				.comparator(new AsymmetricComparator())
				.boundedCache(32).build());
		
		testMultiMethod(Method1.<Integer>builder()
				.comparator(new AsymmetricComparator())
				.boundedCache(8).build());
		
		testMultiMethod(Method1.<Integer>builder()
				.comparator(new AsymmetricComparator())
				.boundedCache(7).build());
		
		testMultiMethod(Method1.<Integer>builder()
				.comparator(new AsymmetricComparator())
				.boundedCache(6).build());
		
		testMultiMethod(Method1.<Integer>builder()
				.comparator(new AsymmetricComparator())
				.boundedCache(5).build());
		
		testMultiMethod(Method1.<Integer>builder()
				.comparator(new AsymmetricComparator())
				.boundedCache(4).build());
	}

}
