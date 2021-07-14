/*******************************************************************************
 * Copyright 2019 The EVL authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package eu.daproject.evl.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

import eu.daproject.evl.AsymmetricComparator;
import eu.daproject.evl.Cases;
import eu.daproject.evl.Method1;
import eu.daproject.evl.Method2;
import eu.daproject.evl.Method3;
import eu.daproject.evl.Method4;
import eu.daproject.evl.Priority;
import eu.daproject.evl.SymmetricComparator;
import eu.daproject.evl.exception.InvocationException;
import eu.daproject.evl.exception.MethodNotAddedException;
import eu.daproject.evl.util.CacheItem;
import eu.daproject.evl.util.ClassTuple;

public class InvocationTests {
	
	@Test
	public void testMethod1() throws Throwable {
		
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
		
		error = false;
		try {
			m.check(E.class);
			
		} catch (InvocationException ex) {
			error = true;
		}
		assertTrue(error);
		
		m.add(Foo.class, "foo", E.class);
		res = m.invoke(e);
		assertEquals(res, 4);
	}
	
	
	@Test
	public void testMethod2() throws Throwable {

		Foo2 foo = new Foo2();
		
		Method2<Integer> m = new Method2<Integer>()
				.comparator(new AsymmetricComparator())
				.add(foo, "foo");

		
		E e = new E();
		
		int res = m.invoke(e, e);
		assertEquals(res, 2);
	}
	
	@Test
	public void testMethod3() throws Throwable {

		Foo3 foo = new Foo3();
		
		Method3<Integer> m = new Method3<Integer>()
				.comparator(new AsymmetricComparator())
				.add(foo, "foo");

		
		E e = new E();
		
		int res = m.invoke(e, e, e);
		assertEquals(res, 3);
	}

	@Test
	public void testMethod4() throws Throwable {

		Foo4 foo = new Foo4();
		
		Method4<Integer> m = new Method4<Integer>()
				.comparator(new AsymmetricComparator())
				.add(foo, "foo");

		
		E e = new E();
		
		int res = m.invoke(e, e, e, e);
		assertEquals(res, 3);
	}
	
	@Test
	public void testNonVirtualParameter() throws Throwable {
	
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
	public void testAmbiguityResolution() throws Throwable {
		
		Foo2 foo = new Foo2();
		E e = new E();
		
		{
			Method2<Integer> m = new Method2<Integer>()
					.comparator(new SymmetricComparator())
					.add(foo, "foo");
	
			boolean error = false;
			try {
				m.invoke(e, e);
			}
			catch (InvocationException ex) {
				error = true;
			}
			assertTrue(error);
			
			error = false;
			try {
				m.check(E.class, E.class);
			}
			catch (InvocationException ex) {
				error = true;
			}
			assertTrue(error);
		}
		
		{
			Method2<Integer> m = new Method2<Integer>()
					.comparator(new SymmetricComparator())
					.cache(new HashMap<ClassTuple, CacheItem>())
					.add(foo, "foo", IA.class, IA.class).data(Priority.valueOf(3))
					.add(foo, "foo", D.class, IA.class).data(Priority.valueOf(2))
					.add(foo, "foo", IA.class, D.class).data(Priority.valueOf(1));
	
			// Highest priority wins.
			int res = m.invoke(e, e);
			assertEquals(res, 2);
		}
		
		{
			Method2<Integer> m = new Method2<Integer>()
					.comparator(new SymmetricComparator())
					.cache(new HashMap<ClassTuple, CacheItem>())
					.add(foo, "foo", IA.class, IA.class)
					.add(foo, "foo", D.class, IA.class)
					.add(foo, "foo", IA.class, D.class);
	
			m.setData(Priority.valueOf(3), IA.class, IA.class);
			m.setData(Priority.valueOf(2), D.class, IA.class);
			m.setData(Priority.valueOf(1), IA.class, D.class);
			
			// Highest priority wins.
			int res = m.invoke(e, e);
			assertEquals(res, 2);
		}
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
	public void testAccess() throws Throwable {
		
		Inner inner = new Inner();
		
		int res = inner.foo(new E());
		assertEquals(res, 1);
	}
}
