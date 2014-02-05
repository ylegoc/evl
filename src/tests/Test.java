package tests;

import java.util.HashMap;

import tests.classes.Bar;
import tests.classes.D;
import tests.classes.E;
import tests.classes.Foo;
import tests.classes.Foo2;
import tests.classes.IA;
import tests.classes.IC;
import evl.base.AsymmetricComparator;
import evl.base.Method1;
import evl.base.Method2;
import evl.base.SymmetricComparator;
import evl.data.DispatchableMethodD;
import evl.data.Method2D;
import evl.data.PrioritySymmetricComparator;
import evl.util.SuperClass;

public class Test {
	
	public static void test1() {
		
		Method1<Integer> m = Method1.<Integer>builder()
									.comparator(new AsymmetricComparator()).build();
		
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
	
		Method2<Integer> m = Method2.<Integer>builder().comparator(new AsymmetricComparator()).build();
		
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
	
		Method1<Integer> m = Method1.<Integer>builder().comparator(new AsymmetricComparator()).build();
		
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
		
		Method2<Integer> m = Method2.<Integer>builder().comparator(new SymmetricComparator()).build();
		
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
		
		Method2D<Integer, Integer> m2 = new Method2D.Builder<Integer, Integer>()
											.comparator(new PrioritySymmetricComparator<Integer>())
											.cache(new HashMap<Method2D.ClassTuple, DispatchableMethodD<Integer>>())
											.build();

		m2 = Method2D.<Integer, Integer>builder()
				.comparator(new PrioritySymmetricComparator<Integer>())
				.cache(new HashMap<Method2D.ClassTuple, DispatchableMethodD<Integer>>())
				.build();

		
		
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
		
		test1();
		test2();
		test3();
		test4();
	}
}
