package org.bitbucket.evl.test;

import java.util.HashMap;

import org.bitbucket.evl.AsymmetricComparator;
import org.bitbucket.evl.DispatchableMethodD;
import org.bitbucket.evl.Method1;
import org.bitbucket.evl.Method2;
import org.bitbucket.evl.Method2D;
import org.bitbucket.evl.PrioritySymmetricComparator;
import org.bitbucket.evl.SymmetricComparator;
import org.bitbucket.evl.exception.InvocationException;
import org.bitbucket.evl.util.Parameter;
import org.junit.Test;

public class BasicTest {
	
	@Test
	public void test1() throws InvocationException {
		
		Foo foo = new Foo();
		
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
		
		try {
			res = m.invoke(e);
			System.out.println("test1 res = " + res);
			
		} catch (InvocationException ex) {
			System.out.println("error 1 : " + ex.getMessage());
		}
	}
	
	@Test
	public void test2() throws InvocationException {

		Foo2 foo = new Foo2();
		
		Method2<Integer> m = new Method2<Integer>()
				.comparator(new AsymmetricComparator())
				.addAll(Foo2.class, "foo", foo);

		
		E e = new E();
		
		int res = m.invoke(e, e);
		System.out.println("test2 res = " + res);
	}
	
	@Test
	public void test3() throws InvocationException {
	
		Bar bar = new Bar();
		
		Method1<Integer> m = new Method1<Integer>()
				.comparator(new AsymmetricComparator())
				.add(Bar.class, "bar", Parameter.types(IA.class, int.class), bar)
				.add(Bar.class, "bar", Parameter.types(D.class, int.class), bar);
		
		E e = new E();
		
		int res = m.invoke(e, 3);
		System.out.println("test3 res = " + res);

		res = m.invoke(e, 5);
		System.out.println("test3 res = " + res);
	}
	
	@Test
	public void test4() throws InvocationException {
		
		Foo2 foo = new Foo2();
		
		E e = new E();
		
		Method2<Integer> m = new Method2<Integer>()
				.comparator(new SymmetricComparator())
				.addAll(Foo2.class, "foo", foo);

		try {
			int res = m.invoke(e, e);
			System.out.println("test4 res = " + res);
			
		} catch (InvocationException ex) {
			System.out.println("error 4 : " + ex.getMessage());
		}
		
		Method2D<Integer, Integer> m2 = new Method2D<Integer, Integer>()
				.comparator(new PrioritySymmetricComparator<Integer>())
				.cache(new HashMap<Method2D.ClassTuple, DispatchableMethodD<Integer>>())
				.add(Foo2.class, "foo", Parameter.types(IA.class, IA.class), foo, 1)
				.add(Foo2.class, "foo", Parameter.types(D.class, IA.class), foo, 2)
				.add(Foo2.class, "foo", Parameter.types(IA.class, D.class), foo, 3);

		int res = m2.invoke(e, e);
		System.out.println("test4 res = " + res);
	}
	
}
