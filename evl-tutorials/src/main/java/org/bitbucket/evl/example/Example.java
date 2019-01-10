package org.bitbucket.evl.example;

import org.bitbucket.evl.Cases;
import org.bitbucket.evl.Method2;
import org.bitbucket.evl.Switch;

/**
 * A simple example with the Switch class.
 *
 */
public class Example {

	public static class A {
	}
	
	public static class B {
	}
	
	public static class C extends B {
	}
	
	public static void run() throws Throwable {
	
		Switch s = Switch.with(new Cases() {
			
			void match(A a) {
				System.out.println("class A");
			}
			
			void match(B b) {
				System.out.println("class B");
			}
		});
		
		s.invoke(new A());
		s.invoke(new B());
		s.invoke(new C());
	}
}
