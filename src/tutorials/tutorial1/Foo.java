package tutorials.tutorial1;

import tutorials.classes.B;
import tutorials.classes.C;

public class Foo {

	public int process(B obj) {
		return 1 + obj.getB();
	}

	public int process(C obj) {
		return 2 + obj.getC();
	}
}
