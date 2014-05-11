package tutorials.tutorial8;

import java.lang.reflect.Method;

import tutorials.classes.A;
import tutorials.classes.B;
import evl.data.Method1D;
import evl.data.predicate.Predicate;
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
		
		Method1D<Integer, Method> process = Predicate.<Integer>method1Builder().build();
						
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
