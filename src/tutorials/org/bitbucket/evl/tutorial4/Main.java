package org.bitbucket.evl.tutorial4;

import org.bitbucket.evl.classes.A;
import org.bitbucket.evl.classes.B;
import org.bitbucket.evl.classes.C;

/**
 * Open-method example.
 * @author yan
 *
 */
public class Main {
	
	public static void main(String[] args) throws Throwable {
		
		A b = new B(1, 2);
		A c = new C(2, -5);
		
		Foo1 foo1 = new Foo1();
		Process.method().addAll(foo1, "process");
		
		Foo2 foo2 = new Foo2();
		Process.method().addAll(foo2, "process");
		
		System.out.println(Process.method().invoke(b));
		System.out.println(Process.method().invoke(c));
	}
}
