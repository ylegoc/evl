package eu.daproject.evl.tutorial5;

import eu.daproject.evl.classes.A;
import eu.daproject.evl.classes.B;
import eu.daproject.evl.classes.J;
import eu.daproject.evl.classes.K;

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
