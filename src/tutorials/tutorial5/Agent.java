package tutorials.tutorial5;

import tutorials.classes.A;
import tutorials.classes.B;

public class Agent {

	public int process(A a, B b) {
		return a.getA() + b.getB();
	}
	
	public int process(B b, A a) {
		return a.getA() - b.getB();
	}
}
