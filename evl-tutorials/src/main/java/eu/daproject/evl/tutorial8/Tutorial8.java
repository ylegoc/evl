package eu.daproject.evl.tutorial8;

import java.lang.invoke.MethodHandle;
import java.util.HashMap;

import eu.daproject.evl.Method2;
import eu.daproject.evl.Priority;
import eu.daproject.evl.SymmetricComparator;
import eu.daproject.evl.classes.A;
import eu.daproject.evl.classes.B;
import eu.daproject.evl.classes.C;
import eu.daproject.evl.util.ClassTuple;

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
		
		
		Method2<Integer> method = new Method2<Integer>()
				.unboundedCache()
				.add(foo);


		System.out.println(method.invoke(b, c));
		
		method = new Method2<Integer>()
				.boundedCache(2)
				.add(foo);

		System.out.println(method.invoke(b, c));
		
		method = new Method2<Integer>()
				.cache(new HashMap<ClassTuple, MethodHandle>())
				.add(foo);

		System.out.println(method.invoke(b, c));
		
		method = new Method2<Integer>()
				.comparator(new SymmetricComparator())
				.cache(new HashMap<ClassTuple, MethodHandle>())
				.add(foo, "match", B.class, A.class).data(Priority.valueOf(1))
				.add(foo, "match", A.class, C.class).data(Priority.valueOf(2));

		System.out.println(method.invoke(b, c));
	}
}
