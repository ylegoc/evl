package org.bitbucket.evl.tutorial1;

import org.bitbucket.evl.Method1;
import org.bitbucket.evl.classes.A;
import org.bitbucket.evl.classes.B;
import org.bitbucket.evl.classes.C;

/**
 * Simple example.
 * @author yan
 *
 */
public class Main {
	
	public static void main(String[] args) throws Throwable {
		
		A b = new B(1, 2);
		A c = new C(2, -5);
		
		Foo foo = new Foo();
		
		Method1<Integer> process = new Method1<Integer>()
						.addAll(Foo.class, "process", foo);
		
		System.out.println(process.invoke(b));
		System.out.println(process.invoke(c));
	}
}
