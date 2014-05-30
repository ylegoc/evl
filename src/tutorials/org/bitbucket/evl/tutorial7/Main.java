package org.bitbucket.evl.tutorial7;

import org.bitbucket.evl.Method2;
import org.bitbucket.evl.classes.A;
import org.bitbucket.evl.classes.B;
import org.bitbucket.evl.util.Parameter;

/**
 * Example of custom method comparator.
 * @author yan
 *
 */
public class Main {
	
	public static void main(String[] args) throws Exception {
		
		A b1 = new B(1, 2);
		A b2 = new B(2, -5);
		
		Agent agent = new Agent();
		
		Method2<Integer> process = new Method2<Integer>()
				.comparator(new ClassNameComparator())
				.add(Agent.class, "process", Parameter.types(A.class, B.class), agent)
				.add(Agent.class, "process", Parameter.types(B.class, A.class), agent);

		System.out.println(process.invoke(b1, b2));
		
	}
}
