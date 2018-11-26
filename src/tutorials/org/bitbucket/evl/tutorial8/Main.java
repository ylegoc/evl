package org.bitbucket.evl.tutorial8;

import java.lang.invoke.MethodHandle;
import java.util.HashMap;

import org.bitbucket.evl.Method1;
import org.bitbucket.evl.Method2;
import org.bitbucket.evl.Priority;
import org.bitbucket.evl.SymmetricComparator;
import org.bitbucket.evl.classes.A;
import org.bitbucket.evl.classes.B;
import org.bitbucket.evl.classes.C;

/**
 * Example with different cache strategies.
 * @author yan
 *
 */
public class Main {
	
	public static void main(String[] args) throws Throwable {
		
		A b = new B(1, 2);
		A c = new C(2, -5);
		
		Foo foo = new Foo();
		
		Method1<Integer> process = new Method1<Integer>()
						.unboundedCache()
						.add(foo, "process");
		
		System.out.println(process.invoke(b));
		System.out.println(process.invoke(c));
		
		
		process = new Method1<Integer>()
				.boundedCache(2)
				.add(foo, "process");

		System.out.println(process.invoke(b));
		System.out.println(process.invoke(c));
		
		
		process = new Method1<Integer>()
				.cache(new HashMap<Class<?>, MethodHandle>())
				.add(foo, "process");

		System.out.println(process.invoke(b));
		System.out.println(process.invoke(c));
		
		
		Method2<Integer> process2 = new Method2<Integer>()
				.cache(new HashMap<Method2.ClassTuple, MethodHandle>())
				.add(foo, "process2");


		System.out.println(process2.invoke(b, c));

		
		Method2<Integer> process2d = new Method2<Integer>()
				.comparator(new SymmetricComparator())
				.cache(new HashMap<Method2.ClassTuple, MethodHandle>())
				.add(foo, "process2", B.class, A.class).data(Priority.valueOf(1))
				.add(foo, "process2", B.class, C.class).data(Priority.valueOf(2));

		System.out.println(process2d.invoke(b, c));

	}
}
