package org.bitbucket.evl.tutorial8;

import org.bitbucket.evl.classes.A;
import org.bitbucket.evl.classes.B;
import org.bitbucket.evl.classes.C;

public class Foo {

	public int process(B obj) {
		return 1 + obj.getB();
	}

	public int process(C obj) {
		return 2 + obj.getC();
	}

	public int process2(B obj1, A obj2) {
		return 1 + obj1.getB() + obj2.getA();
	}
	
	public int process2(B obj1, C obj2) {
		return 1 - obj1.getB() - obj2.getC();
	}
}
