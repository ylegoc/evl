package tutorials.tutorial5;

import tutorials.classes.A;
import tutorials.classes.B;
import evl.base.Method2;
import evl.base.SymmetricComparator;
import evl.data.Method2D;
import evl.data.PrioritySymmetricComparator;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		A b1 = new B(1, 2);
		A b2 = new B(2, -5);
		
		Agent agent = new Agent();
		
		Method2<Integer> process1 = Method2.<Integer>builder()
						.comparator(new SymmetricComparator())
						.build()
						.addAll(Agent.class, "process", agent);

		try {
			System.out.println(process1.invoke(b1, b2));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		ExtendedAgent agent2 = new ExtendedAgent();
		
		Method2<Integer> process2 = Method2.<Integer>builder()
				.comparator(new SymmetricComparator())
				.build()
				.addAll(ExtendedAgent.class, "process", agent2);

		System.out.println(process2.invoke(b1, b2));
		

		Method2D<Integer, Integer> process3 = Method2D.<Integer, Integer>builder()
				.comparator(new PrioritySymmetricComparator<Integer>())
				.build()
				.add(Agent.class.getMethod("process", A.class, B.class), agent, 1)
				.add(Agent.class.getMethod("process", B.class, A.class), agent, 0);

		System.out.println(process3.invoke(b1, b2));

		
		Method2D<Integer, String> process4 = Method2D.<Integer, String>builder()
				.comparator(new PrioritySymmetricComparator<String>())
				.build()
				.add(Agent.class.getMethod("process", A.class, B.class), agent, "first")
				.add(Agent.class.getMethod("process", B.class, A.class), agent, "second");

		// the test with String parameter does not provide consistent order
		// an exception is thrown because the minimum found is not the real minimum
		try {
			System.out.println(process4.invoke(b1, b2));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
