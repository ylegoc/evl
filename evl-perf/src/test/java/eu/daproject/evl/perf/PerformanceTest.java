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
package eu.daproject.evl.perf;

import java.util.Date;
import java.util.HashMap;

import org.junit.Test;

import eu.daproject.evl.AsymmetricComparator;
import eu.daproject.evl.Method1;
import eu.daproject.evl.Method2;
import eu.daproject.evl.Method3;
import eu.daproject.evl.util.CacheItem;

public class PerformanceTest {

	static int N = 1 << 23;
	static int[] indexes;
	static Base[] objects = new Base[8];
	static long randomAccessTime;
	
	static void init() {
		indexes = new int[N];
		
		for (int i = 0; i < N; i++) {
			indexes[i] = (int) (1 + (Math.random() * 7));
		}
		
		objects[0] = new A1();
		objects[1] = new A2();
		objects[2] = new A3();
		objects[3] = new A4();
		objects[4] = new A5();
		objects[5] = new A6();
		objects[6] = new A7();
		objects[7] = new A8();
		
		Date begin = new Date();
		
		int res = 0;
		for (int i = 0; i < N; i++) {
			res += objects[indexes[i]].id;
		}
		
		Date end = new Date();
		randomAccessTime = end.getTime() - begin.getTime();
		
		System.out.println("random access takes " + randomAccessTime + "ms result " + res);
	}
	
	static void testMethod() {
		Date begin = new Date();
		
		int res = 0;
		for (int i = 0; i < N; i++) {
			res += objects[indexes[i]].foo();
		}
		
		Date end = new Date();
		
		System.out.println("method in " + (end.getTime() - begin.getTime() - randomAccessTime) + "ms result " + res);
	}
	
	private static void testInstanceOf() {

		Foo foo = new Foo();
		
		Date begin = new Date();
		
		int res = 0;
		for (int i = 0; i < N; i++) {
			res += foo.processAll(objects[indexes[i]]);
		}
		
		Date end = new Date();
		
		System.out.println("instanceof in " + (end.getTime() - begin.getTime() - randomAccessTime) + "ms result " + res);
	}
	
	private static void testMap() {
		
		Foo foo = new Foo();
		
		Date begin = new Date();
		
		int res = 0;
		for (int i = 0; i < N; i++) {
			res += foo.processAllMap(objects[indexes[i]]);
		}
		
		Date end = new Date();
		
		System.out.println("map in " + (end.getTime() - begin.getTime() - randomAccessTime) + "ms result " + res);
	}
	
	private static void testMethodExtern() {
		
		Foo foo = new Foo();
		
		Date begin = new Date();
		
		int res = 0;
		for (int i = 0; i < N; i++) {
			res += foo.processAllExtern(objects[indexes[i]]);
		}
		
		Date end = new Date();
		
		System.out.println("method extern in " + (end.getTime() - begin.getTime() - randomAccessTime) + "ms result " + res);
	}
	
	private static void testMethodExternArray() {
		
		Foo foo = new Foo();
		
		Date begin = new Date();
		
		int res = 0;
		for (int i = 0; i < N; i++) {
			res += foo.processAllExternArray(objects[indexes[i]]);
		}
		
		Date end = new Date();
		
		System.out.println("method extern array in " + (end.getTime() - begin.getTime() - randomAccessTime) + "ms result " + res);
	}
	
	private static void testMethodReflect() {
		
		Foo foo = new Foo();
		
		Date begin = new Date();
		
		int res = 0;
		for (int i = 0; i < N; i++) {
			res += foo.processAllMethod(objects[indexes[i]]);
		}
		
		Date end = new Date();
		
		System.out.println("method reflect in " + (end.getTime() - begin.getTime() - randomAccessTime) + "ms result " + res);
	}
	
	private static void testMethodHandle() {
		
		Foo foo = new Foo();
		
		Date begin = new Date();
		
		int res = 0;
		for (int i = 0; i < N; i++) {
			res += foo.processAllHandle(objects[indexes[i]]);
		}
		
		Date end = new Date();
		
		System.out.println("method handle in " + (end.getTime() - begin.getTime() - randomAccessTime) + "ms result " + res);
	}
	
	private static void testMethodBoundHandle() {
		
		Foo foo = new Foo();
		
		Date begin = new Date();
		
		int res = 0;
		for (int i = 0; i < N; i++) {
			res += foo.processAllBoundHandle(objects[indexes[i]]);
		}
		
		Date end = new Date();
		
		System.out.println("method bound handle in " + (end.getTime() - begin.getTime() - randomAccessTime) + "ms result " + res);
	}
	
	private static void testMethodBoundHandleOnObject() {
		
		Foo foo = new Foo();
		
		Date begin = new Date();
		
		int res = 0;
		for (int i = 0; i < N; i++) {
			res += foo.processAllBoundHandleOnObject(objects[indexes[i]]);
		}
		
		Date end = new Date();
		
		System.out.println("method bound handle on object in " + (end.getTime() - begin.getTime() - randomAccessTime) + "ms result " + res);
	}
	
	private static void testMethodHandleArray() {
		
		Foo foo = new Foo();
		
		Date begin = new Date();
		
		int res = 0;
		for (int i = 0; i < N; i++) {
			res += foo.processAllHandleArray(objects[indexes[i]]);
		}
		
		Date end = new Date();
		
		System.out.println("method handle array in " + (end.getTime() - begin.getTime() - randomAccessTime) + "ms result " + res);
	}
	
	static void testMultiMethod1(String text, Method1<Integer> m) throws Throwable {
	
		Foo foo = new Foo();
		
		m.add(foo, "processA1", A1.class);
		m.add(foo, "processA2", A2.class);
		m.add(foo, "processA3", A3.class);
		m.add(foo, "processA4", A4.class);
		m.add(foo, "processA5", A5.class);
		m.add(foo, "processA6", A6.class);
		m.add(foo, "processA7", A7.class);
		m.add(foo, "processA8", A8.class);
		
		for (int i = 0; i < 8; i++) {
			m.invoke(objects[i]);
		}
		
		Date begin = new Date();
		
		int res = 0;
		for (int i = 0; i < N; i++) {
			res += m.invoke(objects[indexes[i]]);
		}
		
		Date end = new Date();
		
		System.out.println(text + (end.getTime() - begin.getTime() - randomAccessTime) + "ms result " + res);
	}
	
	static void testMultiMethod2(String text, Method2<Integer> m) throws Throwable {
		
		Foo foo = new Foo();
		
		m.add(foo, "processA1A1", A1.class, A1.class);
		m.add(foo, "processA2A2", A2.class, A2.class);
		m.add(foo, "processA3A3", A3.class, A3.class);
		m.add(foo, "processA4A4", A4.class, A4.class);
		m.add(foo, "processA5A5", A5.class, A5.class);
		m.add(foo, "processA6A6", A6.class, A6.class);
		m.add(foo, "processA7A7", A7.class, A7.class);
		m.add(foo, "processA8A8", A8.class, A8.class);
		
		for (int i = 0; i < 8; i++) {
			m.invoke(objects[i], objects[i]);
		}
		
		Date begin = new Date();
		
		int res = 0;
		for (int i = 0; i < N; i++) {
			Base obj = objects[indexes[i]];
			res += m.invoke(obj, obj);
		}
		
		Date end = new Date();
		
		System.out.println(text + (end.getTime() - begin.getTime() - randomAccessTime) + "ms result " + res);
	}
	
	static void testMultiMethod3(String text, Method3<Integer> m) throws Throwable {

		Foo foo = new Foo();
		
		m.add(foo, "processA1A1A1", A1.class, A1.class, A1.class);
		m.add(foo, "processA2A2A2", A2.class, A2.class, A2.class);
		m.add(foo, "processA3A3A3", A3.class, A3.class, A3.class);
		m.add(foo, "processA4A4A4", A4.class, A4.class, A4.class);
		m.add(foo, "processA5A5A5", A5.class, A5.class, A5.class);
		m.add(foo, "processA6A6A6", A6.class, A6.class, A6.class);
		m.add(foo, "processA7A7A7", A7.class, A7.class, A7.class);
		m.add(foo, "processA8A8A8", A8.class, A8.class, A8.class);
		
		for (int i = 0; i < 8; i++) {
			m.invoke(objects[i], objects[i], objects[i]);
		}
		
		Date begin = new Date();
		
		int res = 0;
		for (int i = 0; i < N; i++) {
			Base obj = objects[indexes[i]];
			res += m.invoke(obj, obj, obj);
		}
		
		Date end = new Date();
		
		System.out.println(text + (end.getTime() - begin.getTime() - randomAccessTime) + "ms result " + res);
	}

	@Test
	public void testAll() throws Throwable {
		
		init();
		testMethod();
		testInstanceOf();
		testMap();
		testMethodExtern();
		testMethodExternArray();
		testMethodReflect();
		testMethodHandle();
		testMethodBoundHandle();
		testMethodBoundHandleOnObject();
		testMethodHandleArray();
		
		testMultiMethod1("method 1 in ", new Method1<Integer>()
								.comparator(new AsymmetricComparator()));
		
		testMultiMethod2("method 2 in ", new Method2<Integer>()
								.comparator(new AsymmetricComparator()));
		
		testMultiMethod3("method 3 in ", new Method3<Integer>()
								.comparator(new AsymmetricComparator()));
		
		testMultiMethod1("method 1 with HashMap cache in ", new Method1<Integer>()
								.comparator(new AsymmetricComparator())
								.cache(new HashMap<Class<?>, CacheItem>()));
		
		testMultiMethod1("method 1 with bounded cache 32 in ", new Method1<Integer>()
				.comparator(new AsymmetricComparator())
				.boundedCache(32));
		
		testMultiMethod1("method 1 with bounded cache 8 in ", new Method1<Integer>()
				.comparator(new AsymmetricComparator())
				.boundedCache(8));
		
		testMultiMethod1("method 1 with bounded cache 4 in ", new Method1<Integer>()
				.comparator(new AsymmetricComparator())
				.boundedCache(4));
	}

}
