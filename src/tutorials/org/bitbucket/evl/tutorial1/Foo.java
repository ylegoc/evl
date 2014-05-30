package org.bitbucket.evl.tutorial1;

import org.bitbucket.evl.classes.B;
import org.bitbucket.evl.classes.C;

public class Foo {
	
	public int process(B obj) {
		return 1 + obj.getB();
	}

	public int process(C obj) {
		return 2 + obj.getC();
	}
}
