package org.bitbucket.evl.tutorial6;

import org.bitbucket.evl.classes.B;

public class ExtendedAgent extends Agent {

	public int process(B b1, B b2) {
		return b1.getB() * b2.getB();
	}
}
