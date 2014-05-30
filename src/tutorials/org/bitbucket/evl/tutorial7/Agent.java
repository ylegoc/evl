package org.bitbucket.evl.tutorial7;

import org.bitbucket.evl.classes.A;
import org.bitbucket.evl.classes.B;

public class Agent {

	public int process(A a, B b) {
		return a.getA() + b.getB();
	}
	
	public int process(B b, A a) {
		return a.getA() - b.getB();
	}
}
