package eu.daproject.evl.tutorial6;

import eu.daproject.evl.classes.B;

public class ExtendedAgent extends Agent {

	public int process(B b1, B b2) {
		return b1.getB() * b2.getB();
	}
}
