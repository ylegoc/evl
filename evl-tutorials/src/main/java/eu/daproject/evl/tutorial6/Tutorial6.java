package eu.daproject.evl.tutorial6;

import eu.daproject.evl.Method2;
import eu.daproject.evl.SymmetricComparator;
import eu.daproject.evl.classes.A;
import eu.daproject.evl.classes.B;
import eu.daproject.evl.exception.InvocationException;

/**
 * Example on how to extend an "agent" class.
 *
 */
public class Tutorial6 {
	
	public static void run() throws Throwable {
		
		A b1 = new B(1, 2);
		A b2 = new B(2, -5);
		
		Agent agent = new Agent();
		
		Method2<Integer> method = new Method2<Integer>()
						.comparator(new SymmetricComparator())
						.add(agent, "process");

		try {
			System.out.println(method.invoke(b1, b2));
		} catch (InvocationException e) {
			System.out.println("error : " + e.getMessage());
		}
		
		ExtendedAgent agent2 = new ExtendedAgent();
		
		method = new Method2<Integer>()
				.comparator(new SymmetricComparator())
				.add(agent2, "process");

		System.out.println(method.invoke(b1, b2));
	}
}
