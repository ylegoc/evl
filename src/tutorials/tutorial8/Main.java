package tutorials.tutorial8;

import tutorials.classes.A;
import tutorials.classes.B;
import evl.data.predicate.PredicateMethod1;
import evl.exceptions.EVLException;

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
						
		process.add(Foo.class.getMethod("process", A.class, int.class), 
					foo, 
					Foo.class.getMethod("test", A.class, int.class));
	
		process.add(Foo.class.getMethod("process", B.class, int.class), 
				foo, 
				Foo.class.getMethod("test", B.class, int.class));

		process.add(Foo.class.getMethod("process2", B.class, int.class), 
				foo, 
				Foo.class.getMethod("test2", B.class, int.class));

		
		System.out.println(process.invoke(b, -1));
		
		try {
			System.out.println(process.invoke(b, 1));
		} catch (EVLException e) {
			System.out.println("no function for x = 1");
		}
		System.out.println(process.invoke(b, 11));
		System.out.println(process.invoke(b, 21));

	}
}
