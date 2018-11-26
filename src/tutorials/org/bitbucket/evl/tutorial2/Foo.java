package org.bitbucket.evl.tutorial2;

import org.bitbucket.evl.Method1;
import org.bitbucket.evl.classes.B;
import org.bitbucket.evl.classes.C;

public class Foo {
	
	private Method1<Integer> process = new Method1<Integer>().add(this, "process");
	
	public int process(Object obj) throws Throwable {
		return process.invoke(obj);
	}
	
	public int process(B obj) {
		return 1 + obj.getB();
	}

	public int process(C obj) {
		return 2 + obj.getC();
	}
}
