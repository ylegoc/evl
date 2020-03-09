package eu.daproject.evl.features.part5;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method1;

public class Class {

	protected static Method1<Integer> foo = new Method1<Integer>();
	
	public Class() {
		
		foo.add(new Cases() {
			
			int match(A a) {
				return a.a;
			}
		});
	}
	
	public int foo(A a) {
		
		try {
			return foo.invoke(a);
		}
		catch (Throwable e) {
			// Cannot happen.
			return 0;
		}
	}
}
