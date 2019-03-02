package eu.daproject.evl.tutorial3;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method2;
import eu.daproject.evl.classes.A;
import eu.daproject.evl.classes.B;
import eu.daproject.evl.classes.C;

/**
 * Binary method example.
 *
 */
public class Tutorial3 {
	
	public static void run() throws Throwable {
		
		A b1 = new B(1, 4);
		A b2 = new B(3, 7);
		A c1 = new C(2, -6);
		
		Method2<Boolean> compare = new Method2<Boolean>().add(new Cases() {
			
			boolean match(A a1, A a2) {
				return a1.getA() == a2.getA();
			}
			
			boolean match(B b1, B b2) {
				return match((A)b1, (A)b2) && b1.getB() == b2.getB();
			}
			
			boolean match(C c1, C c2) {
				return match((A)c1, (A)c2) && c1.getC() == c2.getC();
			}
		});
		
		System.out.println(compare.invoke(b1, b2));
		System.out.println(compare.invoke(b1, b1));
		System.out.println(compare.invoke(b1, c1));
		
		System.out.println("Compare = " + compare);
		compare.printCache();
	}
}
