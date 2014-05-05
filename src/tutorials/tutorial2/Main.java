package tutorials.tutorial2;

import tutorials.classes.A;
import tutorials.classes.B;
import tutorials.classes.C;
import evl.base.Method2;

/**
 * Binary method example.
 * @author yan
 *
 */
public class Main {
	
	public static void main(String[] args) throws Exception {
		
		A b1 = new B(1, 4);
		A b2 = new B(3, 7);
		A c1 = new C(2, -6);
		
		Comparator comparator = new Comparator();
		
		Method2<Boolean> compare = Method2.<Boolean>builder()
						.build()
						.addAll(Comparator.class, "compare", comparator);
		
		System.out.println(compare.invoke(b1, b2));
		System.out.println(compare.invoke(b1, b1));
		System.out.println(compare.invoke(b1, c1));
	}
}
