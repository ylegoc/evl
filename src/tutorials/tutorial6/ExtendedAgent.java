package tutorials.tutorial6;

import tutorials.classes.A;
import tutorials.classes.B;

public class ExtendedAgent extends Agent {

	public int process(B b1, B b2) {
		return process((A)b1, b2);
	}
}
