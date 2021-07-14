package eu.daproject.evl.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method1;
import eu.daproject.evl.Method2;
import eu.daproject.evl.SymmetricComparator;
import eu.daproject.evl.exception.AmbiguousMethodException;
import eu.daproject.evl.exception.BadNonVirtualParameterTypesException;
import eu.daproject.evl.exception.BadNumberOfVirtualParameterTypesException;
import eu.daproject.evl.exception.BadReturnTypeException;
import eu.daproject.evl.exception.MethodNotAddedException;
import eu.daproject.evl.exception.NoMatchingMethodException;

public class ExceptionTests {
	
	@Test
	public void testBadNumberOfVirtualParameterTypesException() throws Throwable {
		
		Foo2 foo = new Foo2();
		
		boolean error = false;
		
		Method2<Integer> m = new Method2<Integer>()
				.returnType(int.class)
				.add(foo, "foo");
		
		try {
			m.add(foo, "foo1");
		}
		catch (BadNumberOfVirtualParameterTypesException e) {
			error = true;
		}
				
		assertTrue(error);
	}
	
	@Test
	public void testBadNonVirtualParameterTypesException() throws Throwable {
		
		Goo goo = new Goo();
		
		boolean error = false;
		
		Method1<Integer> m = new Method1<Integer>()
				.nonVirtualParameterTypes(int.class)
				.returnType(int.class)
				.add(goo, "goo", IA.class, int.class)
				.add(goo, "goo", D.class, int.class);
		
		try {
			m.add(goo, "goo", IC.class, float.class);
		}
		catch (BadNonVirtualParameterTypesException e) {
			error = true;
		}
				
		assertTrue(error);
	}
	
	@Test
	public void testBadReturnTypeException() throws Throwable {
	
		{
			Goo goo = new Goo();
			
			boolean error = false;
			
			Method1<Integer> m = new Method1<Integer>()
					.nonVirtualParameterTypes(int.class)
					.returnType(int.class);
			
			try {
				m.add(goo, "goo", IB.class, int.class);
			}
			catch (BadReturnTypeException e) {
				error = true;
			}

			assertTrue(error);
		}
		
		{
			Hoo hoo = new Hoo();
			
			boolean error = false;
			
			Method1<IA> m = new Method1<IA>()
					.nonVirtualParameterTypes(int.class)
					.returnType(IA.class);
			
			m.add(hoo, "hoo", IC.class, int.class);
			m.add(hoo, "hoo", E.class, int.class);
			
			try {
				m.add(hoo, "hoo", D.class, int.class);	
			}
			catch (BadReturnTypeException e) {
				error = true;
			}
	
			assertTrue(error);
		}
	}
		
	@Test
	public void testNullArgument() throws Throwable {
		
		Foo foo = new Foo();
		
		Method1<Void> m = new Method1<Void>();
		
		boolean error = false;
		try {
			m.invoke(null);
		}
		catch (IllegalArgumentException e) {
			error = true;
		}
		assertTrue(error);
		
		m.add(foo, "foo", IA.class);
		
		try {
			m.invoke(null);
		}
		catch (IllegalArgumentException e) {
			error = true;
		}
		assertTrue(error);
	}
	
	@Test
	public void testNoMatchingMethodException() throws Throwable {
		
		Foo2 foo = new Foo2();
		D d = new D();
		
		Method2<Integer> m = new Method2<Integer>()
				.comparator(new SymmetricComparator())
				.add(foo, "foo");
		
		boolean error = false;
		try {
			m.invoke(d, d);
		}
		catch (NoMatchingMethodException e) {
			error = true;
		}
		assertTrue(error);
	}
	
	@Test
	public void testCacheNoMatchingMethod() throws Throwable {
		
		Method1<Integer> m = new Method1<Integer>();
		
		E e = new E();
		
		boolean error = false;
		try {
			m.invoke(e);
		}
		catch (NoMatchingMethodException ex) {
			error = true;
		}
		
		try {
			m.invoke(e);
		}
		catch (NoMatchingMethodException ex) {
			error = true;
		}
		
		assertTrue(error);
	}
	
	@Test
	public void testOverridable() throws Throwable {

		boolean error = false;
		
		Method1<Void> m = new Method1<Void>();
		
		m.add(new Cases() {
			void match(D d) {
				System.out.println("Match D");
			}
		});
		
		m.add(new Cases() {
			void match(D d) {
				System.out.println("Re-match D");
			}
		});
		
		m.notOverridable();
		
		m.add(new Cases() {
			void match(E d) {
				System.out.println("Match E");
			}
		});
		
		try {
			m.add(new Cases() {
				void match(D d) {
					System.out.println("Re-re-match D");
				}
			});
		}
		catch (MethodNotAddedException e) {
			error = true;
		}
		
		assertTrue(error);
	}
	
	@Test
	public void testFinal() throws Throwable {

		boolean error = false;
		
		Method1<Void> m = new Method1<Void>();
		
		m.add(new Cases() {
			void match(D d) {
				System.out.println("Match D");
			}
		});
				
		m.setFinal();
		
		try {
			m.add(new Cases() {
				void match(E d) {
					System.out.println("Match E");
				}
			});
		}
		catch (MethodNotAddedException e) {
			error = true;
		}
		
		assertTrue(error);
	}
	
	@Test
	public void testAmbiguityResolution() throws Throwable {
		
		Foo2 foo = new Foo2();
		E e = new E();
		
		Method2<Integer> m = new Method2<Integer>()
				.comparator(new SymmetricComparator())
				.add(foo, "foo");

		boolean error = false;
		try {
			m.invoke(e, e);
		}
		catch (AmbiguousMethodException ex) {
			error = true;
		}
		assertTrue(error);
		
		error = false;
		try {
			m.check(E.class, E.class);
		}
		catch (AmbiguousMethodException ex) {
			error = true;
		}
		assertTrue(error);
	}
}
