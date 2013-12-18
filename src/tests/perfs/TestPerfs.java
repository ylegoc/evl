package tests.perfs;

import java.util.AbstractMap;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;

import evl.AsymmetricComparator;
import evl.ClassTuple;
import evl.DispatchableMethod;
import evl.MultiMethod;

public class TestPerfs {

	static int N = 1 << 24;
	static int[] indexes;
	static IA[] objects = new IA[8];
	
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
	
	static void testMultiMethod(MultiMethod<Integer, Void> m) {
	
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
	
	public static void main(String[] args) {
		
		init();
		testMethod();
		testInstanceOf();
		testMap();
		
		testMultiMethod(new MultiMethod<Integer, Void>(1, new AsymmetricComparator<Void>(), new HashMap<ClassTuple, DispatchableMethod<Void>>()));
		testMultiMethod(new MultiMethod<Integer, Void>(1, new AsymmetricComparator<Void>(), new ConcurrentHashMap<ClassTuple, DispatchableMethod<Void>>()));
		
		AbstractMap<ClassTuple, DispatchableMethod<Void>> cache32 = new ConcurrentLinkedHashMap.Builder<ClassTuple, DispatchableMethod<Void>>().maximumWeightedCapacity(32).build();
		testMultiMethod(new MultiMethod<Integer, Void>(1, new AsymmetricComparator<Void>(), cache32));
		
		AbstractMap<ClassTuple, DispatchableMethod<Void>> cache4 = new ConcurrentLinkedHashMap.Builder<ClassTuple, DispatchableMethod<Void>>().maximumWeightedCapacity(4).build();
		testMultiMethod(new MultiMethod<Integer, Void>(1, new AsymmetricComparator<Void>(), cache4));
		
	}

}
