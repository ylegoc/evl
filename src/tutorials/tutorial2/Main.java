package tutorials.tutorial2;

import tutorials.classes.A;
import tutorials.classes.B;
import tutorials.classes.C;

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
		
		System.out.println(foo.process(b));
		System.out.println(foo.process(c));
	}
}
