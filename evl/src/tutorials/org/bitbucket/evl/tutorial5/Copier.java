package org.bitbucket.evl.tutorial5;

import org.bitbucket.evl.classes.A;
import org.bitbucket.evl.classes.B;
import org.bitbucket.evl.classes.J;
import org.bitbucket.evl.classes.K;

public class Copier {

	public void copy(A dest, K src) {
		dest.setA(src.getK());
	}
	
	public void copy(B dest, J src) {
		dest.setB(src.getJ());
	}
	
	public void copy(B dest, K src) {
		copy((A)dest, src);
	}
}
