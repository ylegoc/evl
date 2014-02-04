package tests.proto;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import tests.classes.E;
import tests.classes.Foo;
import tests.classes.IA;

public class TestMethodHandle {

public static void test0() {
		
		try {
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

			
			mt = MethodType.methodType(int.class, double.class);
			mh = lookup.findVirtual(Foo.class, "bar", mt);
			
			i = (int)mh.invokeExact(foo, 1.2);
			System.out.println("i = " + i);

			
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		//Foo.class.getMethod("foo", IA.class);
	}
}
