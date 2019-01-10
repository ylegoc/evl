package org.bitbucket.evl.tutorial5;

import org.bitbucket.evl.Method2;
import org.bitbucket.evl.Priority;
import org.bitbucket.evl.SymmetricComparator;
import org.bitbucket.evl.classes.A;
import org.bitbucket.evl.classes.B;
import org.bitbucket.evl.classes.J;
import org.bitbucket.evl.classes.K;
import org.bitbucket.evl.util.Parameter;

/**
 * Symmetric and asymmetric double dispatch using methods with special name "copy".
 * Example of an ambiguity and how to resolve it.
 */
public class Tutorial5 {
	
	public static void run() throws Throwable {
		
		B b = new B(1, 4);
		K k = new K(3, 7);
		
		Copier copier = new Copier();
		
		Method2<Void> copy1 = new Method2<Void>()
				.add(copier, "copy", Parameter.types(A.class, K.class))
				.add(copier, "copy", Parameter.types(B.class, J.class));
		
		copy1.invoke(b, k);
		
		System.out.println(b.getB() + " == " + k.getJ());
		
		Method2<Void> copy2 = new Method2<Void>()
				.comparator(new SymmetricComparator())
				.add(copier, "copy", Parameter.types(A.class, K.class))
				.add(copier, "copy", Parameter.types(B.class, J.class));

		// The invocation generates an ambiguity. 
		try {
			copy2.invoke(b, k);
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		// It can be resolved explicitly by defining a method for the ambiguous class tuple.
		Method2<Void> copy3 = new Method2<Void>()
				.comparator(new SymmetricComparator())
				.add(copier, "copy", Parameter.types(A.class, K.class))
				.add(copier, "copy", Parameter.types(B.class, J.class))
				.add(copier, "copy", Parameter.types(B.class, K.class));

		copy3.invoke(b, k);

		System.out.println(b.getA() + " == " + k.getK());
		
		// It can be resolved implicitly by changing the priority of a method.
		Method2<Void> copy4 = new Method2<Void>()
				.comparator(new SymmetricComparator())
				.add(copier, "copy", Parameter.types(A.class, K.class))
				.add(copier, "copy", Parameter.types(B.class, J.class)).data(Priority.valueOf(1));

		copy4.invoke(b, k);
		
		System.out.println(b.getA() + " == " + k.getK());
	}
}
