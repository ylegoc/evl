package tutorials.tutorial1;

import tutorials.classes.A;
import tutorials.classes.B;
import tutorials.classes.C;
import evl.base.Method1;

/**
 * Simple example.
 * @author yan
 *
 */
public class Main {
	
	public static void main(String[] args) throws Exception {
		
		A b = new B(1, 2);
		A c = new C(2, -5);
		
		Foo foo = new Foo();
		
		Method1<Integer> process = Method1.<Integer>builder()
						.build()
						.addAll(Foo.class, "process", foo);
		
		System.out.println(process.invoke(b));
		System.out.println(process.invoke(c));
	}
}
