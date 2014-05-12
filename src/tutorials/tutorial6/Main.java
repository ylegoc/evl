package tutorials.tutorial6;

import tutorials.classes.A;
import tutorials.classes.B;
import evl.base.Method2;
import evl.util.Parameter;

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
