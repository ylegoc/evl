package tutorials.tutorial7;

import java.util.HashMap;

import tutorials.classes.A;
import tutorials.classes.B;
import tutorials.classes.C;
import evl.base.Method1;
import evl.base.Method2;
import evl.data.DispatchableMethodD;
import evl.data.Method2D;
import evl.data.PrioritySymmetricComparator;

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
		
		Method1<Integer> process = Method1.<Integer>builder()
						.unboundedCache()
						.build()
						.addAll(Foo.class, "process", foo);
		
		System.out.println(process.invoke(b));
		System.out.println(process.invoke(c));
		
		
		process = Method1.<Integer>builder()
				.boundedCache(2)
				.build()
				.addAll(Foo.class, "process", foo);

		System.out.println(process.invoke(b));
		System.out.println(process.invoke(c));
		
		
		process = Method1.<Integer>builder()
				.cache(new HashMap<Class<?>, DispatchableMethodD<Void>>())
				.build()
				.addAll(Foo.class, "process", foo);

		System.out.println(process.invoke(b));
		System.out.println(process.invoke(c));
		
		
		Method2<Integer> process2 = Method2.<Integer>builder()
				.cache(new HashMap<Method2.ClassTuple, DispatchableMethodD<Void>>())
				.build()
				.addAll(Foo.class, "process2", foo);


		System.out.println(process2.invoke(b, c));

		
		Method2D<Integer, Integer> process2d = Method2D.<Integer, Integer>builder()
				.comparator(new PrioritySymmetricComparator<Integer>())
				.cache(new HashMap<Method2D.ClassTuple, DispatchableMethodD<Integer>>())
				.build()
				.add(Foo.class.getMethod("process2", B.class, A.class), foo, 1)
				.add(Foo.class.getMethod("process2", B.class, C.class), foo, 2);

		System.out.println(process2d.invoke(b, c));

	}
}
