package eu.daproject.evl.tutorial2;

import eu.daproject.evl.Method1;
import eu.daproject.evl.classes.A;
import eu.daproject.evl.classes.B;
import eu.daproject.evl.classes.C;

/**
 * Example of a stateful "agent" that counts objects.
 *
 */
public class Tutorial2 {
	
	public static class Counter {

		public int count = 0;
		
		public void match(B obj) {
			count += obj.getA() + obj.getB();
		}

		public void match(C obj) {
			count += obj.getA() + obj.getC();
		}
	}
	
	public static void run() throws Throwable {
		
		Counter counter = new Counter();
		
		Method1<Void> method = new Method1<Void>().add(counter);
		
		A b = new B(1, 2);
		A c = new C(3, 5);
		
		method.invoke(b);
		method.invoke(c);
		
		System.out.println("Counted " + counter.count);
	}
}
