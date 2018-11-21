package org.bitbucket.evl.proto;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

import org.bitbucket.evl.basictest.E;
import org.bitbucket.evl.basictest.Foo;
import org.bitbucket.evl.basictest.IA;

public class MethodHandleTest {

	public static class MyClass {
		
		public MyClass() {
		}

		public String value() {
			return "me";
		}
	}

	public static class MyClass2 extends MyClass {
		
		public MyClass2() {
		}

		public String value() {
			return "metoo";
		}
	}

	public static class MyClass3 {
		
		public MyClass3() {
		}

		public String value() {
			return "notme";
		}
	}
	
	public <T> void testLambda(Class<?> type, Consumer<?> c, T obj) {
		try {
			// Useless test.
			T testType = (T)type.newInstance();
			Object o = obj;
			//c.accept(obj);
			System.out.println("Test ok");
			
		} catch (InstantiationException | IllegalAccessException e) {
			System.out.println("Test not ok : " + type);
			e.printStackTrace();
		}
	}
	
	public static class Methods {
		
	}
	
	Method[] methods;
	Methods mm;
	
	void testAdd(Methods f) {
		mm = f;
		methods = f.getClass().getDeclaredMethods();
	}
	
	void testExec(Object obj) {
		for (Method m : methods) {
			m.getParameterTypes();
			try {
				m.invoke(mm, obj);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
			
			MethodHandles.Lookup lookup = MethodHandles.lookup();
			MethodHandle methodHandle = null;
			try {
				methodHandle = lookup.findVirtual(m.getDeclaringClass(), m.getName(), MethodType.methodType(m.getReturnType(), m.getParameterTypes()));
				methodHandle.invoke(mm, obj);
			}
			catch (Throwable e) {
				e.printStackTrace();
			}
			
		}
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
		
		
		try {
			i = (int)mh.invokeExact(foo, e);
		} catch (Throwable t) {
			System.out.println("args must be exact");
		}
		
		mt = MethodType.methodType(int.class, double.class);
		mh = lookup.findVirtual(Foo.class, "bar", mt);
		
		i = (int)mh.invokeExact(foo, 1.2);
		System.out.println("i = " + i);
		
		
		//testLambda((Integer x) -> true);
		Callable<String> ca = () -> "Hello";
		
		Consumer<MyClass> co = (x) -> System.out.println("Hello " + x.value());
		testLambda(MyClass.class, co, new MyClass());
		
		Consumer<Integer> co2 = (x) -> System.out.println("Hello " + x);
		testLambda(MyClass.class, co2, 1);
		
		// Ok
        testAdd(new Methods() {
        	
			void match(MyClass c) {
        		System.out.println("Hello " + c.value());
        	}
			
        	void match(MyClass2 c) {
        		System.out.println("World " + c.value());
        	}
        });
        
        testExec(new MyClass2());
        
        // Not ok
        testAdd(new Methods() {
        	void test(MyClass c) {}
        });
        
        testExec(new MyClass3());
	}
	
	public static void main(String[] args) throws Throwable {
	
		MethodHandleTest test = new MethodHandleTest();
		
		test.test0();
	}

}
