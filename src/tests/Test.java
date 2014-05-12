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
import evl.exceptions.EVLException;
import evl.util.Parameter;
import evl.util.SuperClass;

public class Test {
	
	public static void test1() {
		
		Foo foo = new Foo();
		
		try {
			Method1<Integer> m = new Method1<Integer>()
					.comparator(new AsymmetricComparator())
					.add(Foo.class, "foo", Parameter.types(IA.class), foo)
					.add(Foo.class, "foo", Parameter.types(D.class), foo);
		
			
			E e = new E();
			
			int res = m.invoke(e);
			System.out.println("test1 res = " + res);

			res = m.invoke(e);
			System.out.println("test1 res = " + res);

			
			m.add(Foo.class, "foo", Parameter.types(IC.class), foo);
			
			res = m.invoke(e);
			System.out.println("test1 res = " + res);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void test2() {

		Foo2 foo = new Foo2();
		
		try {
			Method2<Integer> m = new Method2<Integer>()
					.comparator(new AsymmetricComparator())
					.addAll(Foo2.class, "foo", foo);

			
			E e = new E();
			
			int res = m.invoke(e, e);
			System.out.println("test2 res = " + res);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void test3() {
	
		Bar bar = new Bar();
		
		try {
			Method1<Integer> m = new Method1<Integer>()
					.comparator(new AsymmetricComparator())
					.add(Bar.class, "bar", Parameter.types(IA.class, int.class), bar)
					.add(Bar.class, "bar", Parameter.types(D.class, int.class), bar);
			

			E e = new E();
			
			int res = m.invoke(e, 3);
			System.out.println("test3 res = " + res);

			res = m.invoke(e, 5);
			System.out.println("test3 res = " + res);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void test4() {
		
		Foo2 foo = new Foo2();
		
		E e = new E();
		
		try {
			Method2<Integer> m = new Method2<Integer>()
					.comparator(new SymmetricComparator())
					.addAll(Foo2.class, "foo", foo);
			
			int res = m.invoke(e, e);
			System.out.println("test4 res = " + res);
			
		} catch (EVLException ex) {
			System.out.println("error : " + ex.getMessage());
		}
		
		try {
			Method2D<Integer, Integer> m2 = new Method2D<Integer, Integer>()
					.comparator(new PrioritySymmetricComparator<Integer>())
					.cache(new HashMap<Method2D.ClassTuple, DispatchableMethodD<Integer>>())
					.add(Foo2.class, "foo", Parameter.types(IA.class, IA.class), foo, 1)
					.add(Foo2.class, "foo", Parameter.types(D.class, IA.class), foo, 2)
					.add(Foo2.class, "foo", Parameter.types(IA.class, D.class), foo, 3);
			
			int res = m2.invoke(e, e);
			System.out.println("test4 res = " + res);
			
		} catch (Exception ex) {
			System.out.println("error : " + ex.getMessage());
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
