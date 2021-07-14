package eu.daproject.evl.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import eu.daproject.evl.Method1;
import eu.daproject.evl.exception.BadNonVirtualParameterTypesException;
import eu.daproject.evl.exception.BadReturnTypeException;
import eu.daproject.evl.exception.InvocationException;

public class ExceptionTests {
	
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
	public void testCacheNoMatchingMethod() throws Throwable {
		
		Method1<Integer> m = new Method1<Integer>();
		
		E e = new E();
		
		boolean error = false;
		try {
			m.invoke(e);
		}
		catch (InvocationException ex) {
			error = true;
		}
		
		try {
			m.invoke(e);
		}
		catch (InvocationException ex) {
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
}
