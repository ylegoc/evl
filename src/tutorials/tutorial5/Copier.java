package tutorials.tutorial5;

import tutorials.classes.A;
import tutorials.classes.B;
import tutorials.classes.J;
import tutorials.classes.K;

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
