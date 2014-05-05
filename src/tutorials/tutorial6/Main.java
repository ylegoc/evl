package tutorials.tutorial6;

import tutorials.classes.A;
import tutorials.classes.B;
import tutorials.tutorial6.Agent;
import evl.base.Method2;

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
		
		Method2<Integer> process = Method2.<Integer>builder()
				.comparator(new ClassNameComparator())
				.build()
				.add(Agent.class.getMethod("process", A.class, B.class), agent)
				.add(Agent.class.getMethod("process", B.class, A.class), agent);

		System.out.println(process.invoke(b1, b2));
		
	}
}
