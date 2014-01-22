package tests;

import java.util.HashMap;

import tests.classes.D;
import tests.classes.E;
import tests.classes.Foo;
import tests.classes.IA;
import tests.classes.IC;
import evl.AsymmetricComparator;
import evl.DispatchableMethod;
import evl.Method1;
import evl.MultiMethod;
import evl.util.SuperClass;

public class Test {

	public static void test1() {
		
		MultiMethod<Integer, Void> m = new Method1<Integer, Void>(new AsymmetricComparator<Void>(), new HashMap<Class<?>, DispatchableMethod<Void>>());
		
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
			System.out.println("error");
		}
	}
	
	public static void test2() {
	/*	
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
		}*/
	}
	
	public static void test3() {
	/*	
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
		}*/
	}
	
	public static void test4() {
		/*
		MultiMethod<Integer, Void> m = new MultiMethod<Integer, Void>(2, new SymmetricComparator<Void>(), new HashMap<ClassTuple, DispatchableMethod<Void>>());
		
		Foo2 foo = new Foo2();
		
		E e = new E();
		
		try {
		
			m.add(Foo2.class.getMethod("foo", IA.class, IA.class), foo);
			m.add(Foo2.class.getMethod("foo", D.class, IA.class), foo);
			m.add(Foo2.class.getMethod("foo", IA.class, D.class), foo);
			
			int res = m.invoke(e, e);
			System.out.println("m res = " + res);
			
		} catch (Exception ex) {
			System.out.println("error");
		}
		
		MultiMethod<Integer, Integer> m2 = new MultiMethod<Integer, Integer>(2, new PrioritySymmetricComparator<Integer>(), new HashMap<ClassTuple, DispatchableMethod<Integer>>());
		
		try {
			
			m2.add(Foo2.class.getMethod("foo", IA.class, IA.class), foo, 1);
			m2.add(Foo2.class.getMethod("foo", D.class, IA.class), foo, 2);
			m2.add(Foo2.class.getMethod("foo", IA.class, D.class), foo, 3);
			
			int res = m2.invoke(e, e);
			System.out.println("m2 res = " + res);
			
		} catch (Exception ex) {
			System.out.println("error");
		}*/
	}
	
	public static void main(String[] args) {
	
		HashMap<Class<?>, Integer> classDistanceMap = SuperClass.calculate(E.class);
		
		System.out.println(classDistanceMap);
		
		test1();
		test2();
		test3();
		test4();
	}
}
