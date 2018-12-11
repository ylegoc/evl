package org.bitbucket.evl.tutorial9;

import org.bitbucket.evl.classes.A;
import org.bitbucket.evl.classes.B;
import org.bitbucket.evl.exception.InvocationException;
import org.bitbucket.evl.predicate.Predicate;
import org.bitbucket.evl.predicate.PredicateMethod1;

/**
 * Example with predicate dispatch in data.
 * 
 */
public class Tutorial9 {
	
	public static class Foo {

		public int process(A obj, int x) {
			return 1;
		}
		
		public boolean test(A obj, int x) {
			return (x < 0);
		}

		public int process(B obj, int x) {
			return 2;
		}
		
		public boolean test(B obj, int x) {
			return (x >= 10 && x < 20);
		}
		
		public int process2(B obj, int x) {
			return 3;
		}
		
		public boolean test2(B obj, int x) {
			return (x >= 20);
		}
	}
	
	public static void run() throws Throwable {
		
		A b = new B(2, -5);
		
		Foo foo = new Foo();
		
		PredicateMethod1<Integer> predicateMethod = new PredicateMethod1<Integer>();
						
		predicateMethod.add(foo, "process", A.class, int.class).data(new Predicate(foo, "test", A.class, int.class));
		predicateMethod.add(foo, "process", B.class, int.class).data(new Predicate(foo, "test", B.class, int.class));
		predicateMethod.add(foo, "process2", B.class, int.class).data(new Predicate(foo, "test2", B.class, int.class));
		
		System.out.println(predicateMethod.invoke(b, -1));
		
		try {
			System.out.println(predicateMethod.invoke(b, 1));
		}
		catch (InvocationException e) {
			System.out.println("no function for x = 1");
		}
		
		System.out.println(predicateMethod.invoke(b, 11));
		System.out.println(predicateMethod.invoke(b, 21));
		
		System.out.println("PredicateMethod = " + predicateMethod);
	}
}
