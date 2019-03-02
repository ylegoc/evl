package eu.daproject.evl.tutorial1;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method1;
import eu.daproject.evl.classes.A;
import eu.daproject.evl.classes.B;
import eu.daproject.evl.classes.C;

/**
 * Simple example showing the minimum amount of code to be written for defining a multi-method.
 * 
 */
public class Tutorial1 {
	
	public static void run() throws Throwable {

		Method1<Integer> method = new Method1<Integer>().add(new Cases() {

			int match(B obj) {
				return 1 + obj.getB();
			}

			int match(C obj) {
				return 2 + obj.getC();
			}
		});
		
		A b = new B(1, 2);
		A c = new C(2, -5);
		
		System.out.println(method.invoke(b));
		System.out.println(method.invoke(c));
		
		System.out.println("Method = " + method);
		
		method.printCache();
	}
}
