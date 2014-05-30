package org.bitbucket.evl.tutorial8;

import java.util.HashMap;

import org.bitbucket.evl.DispatchableMethodD;
import org.bitbucket.evl.Method1;
import org.bitbucket.evl.Method2;
import org.bitbucket.evl.Method2D;
import org.bitbucket.evl.PrioritySymmetricComparator;
import org.bitbucket.evl.classes.A;
import org.bitbucket.evl.classes.B;
import org.bitbucket.evl.classes.C;
import org.bitbucket.evl.util.Parameter;

/**
 * Example with different cache strategies.
 * @author yan
 *
 */
public class Main {
	
	public static void main(String[] args) throws Exception {
		
		A b = new B(1, 2);
		A c = new C(2, -5);
		
		Foo foo = new Foo();
		
		Method1<Integer> process = new Method1<Integer>()
						.unboundedCache()
						.addAll(Foo.class, "process", foo);
		
		System.out.println(process.invoke(b));
		System.out.println(process.invoke(c));
		
		
		process = new Method1<Integer>()
				.boundedCache(2)
				.addAll(Foo.class, "process", foo);

		System.out.println(process.invoke(b));
		System.out.println(process.invoke(c));
		
		
		process = new Method1<Integer>()
				.cache(new HashMap<Class<?>, DispatchableMethodD<Void>>())
				.addAll(Foo.class, "process", foo);

		System.out.println(process.invoke(b));
		System.out.println(process.invoke(c));
		
		
		Method2<Integer> process2 = new Method2<Integer>()
				.cache(new HashMap<Method2.ClassTuple, DispatchableMethodD<Void>>())
				.addAll(Foo.class, "process2", foo);


		System.out.println(process2.invoke(b, c));

		
		Method2D<Integer, Integer> process2d = new Method2D<Integer, Integer>()
				.comparator(new PrioritySymmetricComparator<Integer>())
				.cache(new HashMap<Method2D.ClassTuple, DispatchableMethodD<Integer>>())
				.add(Foo.class, "process2", Parameter.types(B.class, A.class), foo, 1)
				.add(Foo.class, "process2", Parameter.types(B.class, C.class), foo, 2);

		System.out.println(process2d.invoke(b, c));

	}
}
