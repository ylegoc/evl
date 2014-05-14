package tutorials.tutorial9;

import tutorials.classes.A;
import tutorials.classes.B;
import evl.data.predicate.PredicateMethod1;
import evl.exceptions.InvocationException;
import evl.util.Parameter;

/**
 * Example with different cache strategies.
 * @author yan
 *
 */
public class Main {
	
	public static void main(String[] args) throws Exception {
		
		A b = new B(2, -5);
		
		Foo foo = new Foo();
		
		PredicateMethod1<Integer> process = new PredicateMethod1<Integer>();
						
		process.add(Foo.class, "process", Parameter.types(A.class, int.class), 
					foo, 
					Foo.class.getMethod("test", A.class, int.class));
	
		process.add(Foo.class, "process", Parameter.types(B.class, int.class), 
				foo, 
				Foo.class.getMethod("test", B.class, int.class));

		process.add(Foo.class, "process2", Parameter.types(B.class, int.class), 
				foo, 
				Foo.class.getMethod("test2", B.class, int.class));

		
		System.out.println(process.invoke(b, -1));
		
		try {
			System.out.println(process.invoke(b, 1));
		} catch (InvocationException e) {
			System.out.println("no function for x = 1");
		}
		System.out.println(process.invoke(b, 11));
		System.out.println(process.invoke(b, 21));

	}
}
