package eu.daproject.evl.tests;

import eu.daproject.evl.Method1;

public class Inner {

	public Method1<Integer> m = new Method1<Integer>();
	
	public Inner() {
		m.add(this, "fooMatch");
	}
	
	protected int fooMatch(IA a) {
		return 0;
	}
	
	protected int fooMatch(E a) {
		return 1;
	}
	
	public int foo(IA a) throws Throwable {
		return m.invoke(a);
	}
	
}
