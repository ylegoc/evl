package eu.daproject.evl.features.part5;

import eu.daproject.evl.Method1;

public class Class {

	protected Method1<Integer> m = new Method1<Integer>();
	
	public Class() {
		m.add(this, "fooMatch");
	}
	
	protected int fooMatch(A a) {
		return a.a;
	}
	
	public int foo(A a) throws Throwable {
		return m.invoke(a);
	}
}
