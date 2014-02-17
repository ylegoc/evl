package tutorials.tutorial2;

import tutorials.classes.A;
import tutorials.classes.B;
import tutorials.classes.C;

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
