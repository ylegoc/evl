package org.bitbucket.evl.tutorial3;

import org.bitbucket.evl.Method2;
import org.bitbucket.evl.classes.A;
import org.bitbucket.evl.classes.B;
import org.bitbucket.evl.classes.C;

/**
 * Binary method example.
 * @author yan
 *
 */
public class Main {
	
	public static void main(String[] args) throws Throwable {
		
		A b1 = new B(1, 4);
		A b2 = new B(3, 7);
		A c1 = new C(2, -6);
		
		Comparator comparator = new Comparator();
		
		Method2<Boolean> compare = new Method2<Boolean>()
						.addAll(Comparator.class, "compare", comparator);
		
		System.out.println(compare.invoke(b1, b2));
		System.out.println(compare.invoke(b1, b1));
		System.out.println(compare.invoke(b1, c1));
	}
}
