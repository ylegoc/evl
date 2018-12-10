package org.bitbucket.evl.tutorial7;

import org.bitbucket.evl.Cases;
import org.bitbucket.evl.Method2;
import org.bitbucket.evl.MethodComparator;
import org.bitbucket.evl.MethodItem;
import org.bitbucket.evl.classes.A;
import org.bitbucket.evl.classes.B;

/**
 * Example of a custom method comparator.
 *
 */
public class Main {
	
	public static class ClassNameComparator extends MethodComparator {

		@Override
		public int compare(MethodItem m1, MethodItem m2) {
			
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
	
	public static void main(String[] args) throws Throwable {
		
		A b1 = new B(1, 2);
		A b2 = new B(2, -5);
		
		Method2<Integer> process = new Method2<Integer>()
				.comparator(new ClassNameComparator())
				.add(new Cases() {
					public int match(A a, B b) {
						return a.getA() + b.getB();
					}
				
					public int match(B b, A a) {
						return a.getA() - b.getB();
					}
				});

		System.out.println(process.invoke(b1, b2));
		
	}
}
