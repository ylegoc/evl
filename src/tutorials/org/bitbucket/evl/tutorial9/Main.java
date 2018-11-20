package org.bitbucket.evl.tutorial9;

import org.bitbucket.evl.classes.A;
import org.bitbucket.evl.classes.B;
import org.bitbucket.evl.exception.InvocationException;
import org.bitbucket.evl.predicate.Predicate;
import org.bitbucket.evl.predicate.PredicateMethod1;

/**
 * Example with predicate dispatch in data.
 * @author yan
 *
 */
public class Main {
	
	public static void main(String[] args) throws Throwable {
		
		A b = new B(2, -5);
		
		Foo foo = new Foo();
		
		PredicateMethod1<Integer> process = new PredicateMethod1<Integer>();
						
		process.add(foo, "process", A.class, int.class).data(new Predicate(foo, "test", A.class, int.class));
		process.add(foo, "process", B.class, int.class).data(new Predicate(foo, "test", B.class, int.class));
		process.add(foo, "process2", B.class, int.class).data(new Predicate(foo, "test2", B.class, int.class));
		
		System.out.println(process.invoke(b, -1));
		
		try {
			System.out.println(process.invoke(b, 1));
		}
		catch (InvocationException e) {
			System.out.println("no function for x = 1");
		}
		
		System.out.println(process.invoke(b, 11));
		System.out.println(process.invoke(b, 21));

	}
}
