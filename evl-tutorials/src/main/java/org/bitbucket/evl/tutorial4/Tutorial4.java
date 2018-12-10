package org.bitbucket.evl.tutorial4;

import org.bitbucket.evl.classes.A;
import org.bitbucket.evl.classes.B;
import org.bitbucket.evl.classes.C;

/**
 * "Open-method" example. A singleton multi-method can be configured anywhere.
 *
 */
public class Tutorial4 {
	
	public static class Foo1 {

		public int match(B obj) {
			return 1 + obj.getB();
		}
	}
	
	public static class Foo2 {

		public int match(C obj) {
			return 2 + obj.getC();
		}
	}
	
	public static void run() throws Throwable {
		
		A b = new B(1, 2);
		A c = new C(2, -5);
		
		Foo1 foo1 = new Foo1();
		Process.instance().add(foo1);
		
		Foo2 foo2 = new Foo2();
		Process.instance().add(foo2);
		
		System.out.println(Process.instance().invoke(b));
		System.out.println(Process.instance().invoke(c));
	}
}
