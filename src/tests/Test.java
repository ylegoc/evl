package tests;

import java.util.HashMap;

import tests.classes.Bar;
import tests.classes.D;
import tests.classes.E;
import tests.classes.Foo;
import tests.classes.Foo2;
import tests.classes.IA;
import tests.classes.IC;
import evl.AsymmetricComparator;
import evl.ClassTuple;
import evl.DispatchableMethod;
import evl.MultiMethod;
import evl.util.SuperClass;

public class Test {

	public static void test1() {
		
		MultiMethod<Integer, Void> m = new MultiMethod<Integer, Void>(1, new AsymmetricComparator<Void>(), new HashMap<ClassTuple, DispatchableMethod<Void>>());
		
		Foo foo = new Foo();
		
		E e = new E();
		
		try {
		
			m.add(Foo.class.getMethod("foo", IA.class), foo);
			m.add(Foo.class.getMethod("foo", D.class), foo);
			
			int res = m.invoke(e);
			System.out.println("res = " + res);

			res = m.invoke(e);
			System.out.println("res = " + res);

			
			m.add(Foo.class.getMethod("foo", IC.class), foo);
			
			res = m.invoke(e);
			System.out.println("res = " + res);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void test2() {
		
		MultiMethod<Integer, Void> m = new MultiMethod<Integer, Void>(2, new AsymmetricComparator<Void>(), new HashMap<ClassTuple, DispatchableMethod<Void>>());
		
		Foo2 foo = new Foo2();
		
		E e = new E();
		
		try {
		
			m.add(Foo2.class.getMethod("foo", IA.class, IA.class), foo);
			m.add(Foo2.class.getMethod("foo", D.class, IA.class), foo);
			m.add(Foo2.class.getMethod("foo", IA.class, D.class), foo);
			
			int res = m.invoke(e, e);
			System.out.println("res = " + res);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void test3() {
		
		MultiMethod<Integer, Void> m = new MultiMethod<Integer, Void>(1, new AsymmetricComparator<Void>(), new HashMap<ClassTuple, DispatchableMethod<Void>>());
		
		Bar bar = new Bar();
		
		E e = new E();
		
		try {
		
			m.add(Bar.class.getMethod("bar", IA.class, int.class), bar);
			m.add(Bar.class.getMethod("bar", D.class, int.class), bar);
			
			int res = m.invoke(e, 3);
			System.out.println("res = " + res);

			res = m.invoke(e, 5);
			System.out.println("res = " + res);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
	
		HashMap<Class<?>, Integer> classDistanceMap = SuperClass.calculate(E.class);
		
		System.out.println(classDistanceMap);
		
		test1();
		test2();
		test3();
		
	}
}
