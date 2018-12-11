package org.bitbucket.evl.tutorial7;

import org.bitbucket.evl.Cases;
import org.bitbucket.evl.Method2;
import org.bitbucket.evl.MatchMethodComparator;
import org.bitbucket.evl.MatchMethodItem;
import org.bitbucket.evl.classes.A;
import org.bitbucket.evl.classes.B;

/**
 * Example of a custom method comparator.
 *
 */
public class Tutorial7 {
	
	public static class ClassNameComparator extends MatchMethodComparator {

		@Override
		public int compare(MatchMethodItem m1, MatchMethodItem m2) {
			
			int length = m1.getClassTuple().get().length;
			String name1 = "";
			String name2 = "";
			
			for (int i = 0; i < length; ++i) {
				name1 += m1.getClassTuple().get()[i].getName() + ";";
				name2 += m2.getClassTuple().get()[i].getName() + ";";
			}
			
			int result = name1.compareTo(name2);
			
			return result;
		}

	}
	
	public static void run() throws Throwable {
		
		A b1 = new B(1, 2);
		A b2 = new B(2, -5);
		
		Method2<Integer> method = new Method2<Integer>()
				.comparator(new ClassNameComparator())
				.add(new Cases() {
					public int match(A a, B b) {
						return a.getA() + b.getB();
					}
				
					public int match(B b, A a) {
						return a.getA() - b.getB();
					}
				});

		System.out.println(method.invoke(b1, b2));
	}
}
