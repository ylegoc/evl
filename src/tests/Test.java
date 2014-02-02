package tests;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.HashMap;

import tests.classes.Bar;
import tests.classes.D;
import tests.classes.E;
import tests.classes.Foo;
import tests.classes.Foo2;
import tests.classes.IA;
import tests.classes.IC;
import evl.AsymmetricComparator;
import evl.DispatchableMethod;
import evl.Method1;
import evl.Method2;
import evl.PrioritySymmetricComparator;
import evl.SymmetricComparator;
import evl.util.SuperClass;

public class Test {

	public static void test0() {
		
		try {
			MethodHandles.Lookup lookup = MethodHandles.lookup();
			// mt is (char,char)String
			MethodType mt = MethodType.methodType(String.class, char.class, char.class);
			MethodHandle mh = lookup.findVirtual(String.class, "replace", mt);
			String s = (String) mh.invokeExact("daddy", 'd', 'n');
			System.out.println("s = " + s);
		
			Foo foo = new Foo();
			E e = new E();
			
			mt = MethodType.methodType(int.class, IA.class);
			mh = lookup.findVirtual(Foo.class, "foo", mt);
			
			int i = (int)mh.invokeExact(foo, (IA)e);
			System.out.println("i = " + i);
			
			i = (int)mh.invoke(foo, e);
			System.out.println("i = " + i);

			
			mt = MethodType.methodType(int.class, double.class);
			mh = lookup.findVirtual(Foo.class, "bar", mt);
			
			i = (int)mh.invokeExact(foo, 1.2);
			System.out.println("i = " + i);

			
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		//Foo.class.getMethod("foo", IA.class);
	}
	
	public static void test1() {
		
		Method1<Integer, Void> m = new Method1<Integer, Void>(new AsymmetricComparator<Void>(), new HashMap<Class<?>, DispatchableMethod<Void>>());
		
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
	
		Method2<Integer, Void> m = new Method2<Integer, Void>(new AsymmetricComparator<Void>(), new HashMap<Method2.ClassTuple, DispatchableMethod<Void>>());
		
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
	
		Method1<Integer, Void> m = new Method1<Integer, Void>(new AsymmetricComparator<Void>(), new HashMap<Class<?>, DispatchableMethod<Void>>());
		
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
	
	public static void test4() {
		
		Method2<Integer, Void> m = new Method2<Integer, Void>(new SymmetricComparator<Void>(), new HashMap<Method2.ClassTuple, DispatchableMethod<Void>>());
		
		Foo2 foo = new Foo2();
		
		E e = new E();
		
		try {
		
			m.add(Foo2.class.getMethod("foo", IA.class, IA.class), foo);
			m.add(Foo2.class.getMethod("foo", D.class, IA.class), foo);
			m.add(Foo2.class.getMethod("foo", IA.class, D.class), foo);
			
			int res = m.invoke(e, e);
			System.out.println("m res = " + res);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		Method2<Integer, Integer> m2 = new Method2<Integer, Integer>(new PrioritySymmetricComparator<Integer>(), new HashMap<Method2.ClassTuple, DispatchableMethod<Integer>>());
		
		try {
			
			m2.add(Foo2.class.getMethod("foo", IA.class, IA.class), foo, 1);
			m2.add(Foo2.class.getMethod("foo", D.class, IA.class), foo, 2);
			m2.add(Foo2.class.getMethod("foo", IA.class, D.class), foo, 3);
			
			int res = m2.invoke(e, e);
			System.out.println("m2 res = " + res);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
	
		HashMap<Class<?>, Integer> classDistanceMap = SuperClass.calculate(E.class);
		
		System.out.println(classDistanceMap);
		
		test0();
		test1();
		test2();
		test3();
		test4();
	}
}
