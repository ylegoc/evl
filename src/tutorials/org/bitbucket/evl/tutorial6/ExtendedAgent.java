package org.bitbucket.evl.tutorial6;

import org.bitbucket.evl.classes.A;
import org.bitbucket.evl.classes.B;

public class ExtendedAgent extends Agent {

	public int process(B b1, B b2) {
		return process((A)b1, b2);
	}
}
