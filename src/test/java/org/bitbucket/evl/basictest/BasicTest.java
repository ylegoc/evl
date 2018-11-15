package org.bitbucket.evl.basictest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.invoke.MethodHandle;
import java.util.HashMap;

import org.bitbucket.evl.AsymmetricComparator;
import org.bitbucket.evl.Method1;
import org.bitbucket.evl.Method2;
import org.bitbucket.evl.PrioritySymmetricComparator;
import org.bitbucket.evl.SymmetricComparator;
import org.bitbucket.evl.exception.InvocationException;
import org.junit.Test;

public class BasicTest {
	
	@Test
	public void test1() throws Throwable {
		
		Foo foo = new Foo();
		
		Method1<Integer> m = new Method1<Integer>()
				.comparator(new AsymmetricComparator())
				.add(foo, "foo", IA.class)
				.add(foo, "foo", D.class);
		
		E e = new E();
		
		// Adds a new entry in the cache.
		int res = m.invoke(e);
		assertEquals(res, 2);

		// Use the cache.
		res = m.invoke(e);
		assertEquals(res, 2);
		
		// Add a method that will lead to an ambiguity.
		m.add(foo, "foo", IC.class);
		
		boolean error = false;
		try {
			res = m.invoke(e);
			
		} catch (InvocationException ex) {
			error = true;
		}
		assertTrue(error);
		
		m.add(Foo.class, "foo", E.class);
		res = m.invoke(e);
		assertEquals(res, 4);
	}
	
	
	@Test
	public void test2() throws Throwable {

		Foo2 foo = new Foo2();
		
		Method2<Integer> m = new Method2<Integer>()
				.comparator(new AsymmetricComparator())
				.addAll(foo, "foo");

		
		E e = new E();
		
		int res = m.invoke(e, e);
		assertEquals(res, 2);
	}
	
	@Test
	public void test3() throws Throwable {
	
		Bar bar = new Bar();
		
		Method1<Integer> m = new Method1<Integer>()
				.comparator(new AsymmetricComparator())
				.add(bar, "bar", IA.class, int.class)
				.add(bar, "bar", D.class, int.class);
		
		E e = new E();
		
		int res = m.invoke(e, 3);
		assertEquals(res, 3);

		res = m.invoke(e, 5);
		assertEquals(res, 5);
	}
	
	@Test
	public void test4() throws Throwable {
		
		Foo2 foo = new Foo2();
		
		E e = new E();
		
		Method2<Integer> m = new Method2<Integer>()
				.comparator(new SymmetricComparator())
				.addAll(foo, "foo");

		boolean error = false;
		try {
			m.invoke(e, e);
			
		} catch (InvocationException ex) {
			error = true;
		}
		assertTrue(error);
		
		Method2<Integer> m2 = new Method2<Integer>()
				.comparator(new PrioritySymmetricComparator())
				.cache(new HashMap<Method2.ClassTuple, MethodHandle>())
				.add(foo, "foo", IA.class, IA.class).data(1)
				.add(foo, "foo", D.class, IA.class).data(2)
				.add(foo, "foo", IA.class, D.class).data(3);

		// Smallest priority wins.
		int res = m2.invoke(e, e);
		assertEquals(res, 2);
	}
	
}
