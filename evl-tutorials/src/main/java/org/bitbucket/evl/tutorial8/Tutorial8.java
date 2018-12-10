package org.bitbucket.evl.tutorial8;

import java.lang.invoke.MethodHandle;
import java.util.HashMap;

import org.bitbucket.evl.Method2;
import org.bitbucket.evl.Priority;
import org.bitbucket.evl.SymmetricComparator;
import org.bitbucket.evl.classes.A;
import org.bitbucket.evl.classes.B;
import org.bitbucket.evl.classes.C;

/**
 * Example with different cache strategies.
 * Disambiguate with Priority data.
 * 
 */
public class Tutorial8 {
	
	public static class Foo {

		public int match(B obj1, A obj2) {
			return 1 + obj1.getB() + obj2.getA();
		}
		
		public int match(B obj1, C obj2) {
			return 1 - obj1.getB() - obj2.getC();
		}
		
		public int match(A obj1, C obj2) {
			return 1 - obj1.getA() - obj2.getC();
		}
	}

	
	public static void run() throws Throwable {
		
		A b = new B(1, 2);
		A c = new C(2, -5);
		
		Foo foo = new Foo();
		
		
		Method2<Integer> process2 = new Method2<Integer>()
				.unboundedCache()
				.add(foo);


		System.out.println(process2.invoke(b, c));
		
		process2 = new Method2<Integer>()
				.boundedCache(2)
				.add(foo);

		System.out.println(process2.invoke(b, c));
		
		
		process2 = new Method2<Integer>()
				.cache(new HashMap<Method2.ClassTuple, MethodHandle>())
				.add(foo);

		System.out.println(process2.invoke(b, c));
		
		
		process2 = new Method2<Integer>()
				.comparator(new SymmetricComparator())
				.cache(new HashMap<Method2.ClassTuple, MethodHandle>())
				.add(foo, "match", B.class, A.class).data(Priority.valueOf(1))
				.add(foo, "match", A.class, C.class).data(Priority.valueOf(2));

		System.out.println(process2.invoke(b, c));

	}
}
