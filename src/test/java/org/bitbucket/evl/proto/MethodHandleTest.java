package org.bitbucket.evl.proto;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import org.bitbucket.evl.basictest.E;
import org.bitbucket.evl.basictest.Foo;
import org.bitbucket.evl.basictest.IA;
import org.junit.Test;

public class MethodHandleTest {

	MethodHandle mh;
	Object object;
	
	public void testCall(Object... args) throws Throwable {
		mh.invokeWithArguments(args);
		System.out.println("testCall ok");
	}
	
	public void test0() throws Throwable {
		
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
		
		MethodHandle nm = mh.bindTo(foo);
		nm.invoke(e);
		
		this.mh = nm;
		
		testCall(e);
		
		try {
			i = (int)mh.invokeExact(foo, e);
		} catch (Throwable t) {
			System.out.println("args must be exact");
		}
		
		mt = MethodType.methodType(int.class, double.class);
		mh = lookup.findVirtual(Foo.class, "bar", mt);
		
		i = (int)mh.invokeExact(foo, 1.2);
		System.out.println("i = " + i);
	}

}
