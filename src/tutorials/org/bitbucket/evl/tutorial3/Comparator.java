package org.bitbucket.evl.tutorial3;

import org.bitbucket.evl.classes.A;
import org.bitbucket.evl.classes.B;
import org.bitbucket.evl.classes.C;

public class Comparator {

	public boolean compare(A a1, A a2) {
		return a1.getA() == a2.getA();
	}
	
	public boolean compare(B b1, B b2) {
		return compare((A)b1, (A)b2) && b1.getB() == b2.getB();
	}
	
	public boolean compare(C c1, C c2) {
		return compare((A)c1, (A)c2) && c1.getC() == c2.getC();
	}
}
