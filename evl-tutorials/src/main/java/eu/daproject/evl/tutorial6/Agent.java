package eu.daproject.evl.tutorial6;

import eu.daproject.evl.classes.A;
import eu.daproject.evl.classes.B;

public class Agent {

	public int process(A a, B b) {
		return a.getA() + b.getB();
	}
	
	public int process(B b, A a) {
		return a.getA() - b.getB();
	}
}
