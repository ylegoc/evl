package tutorials.tutorial7;

import tutorials.classes.A;
import tutorials.classes.B;
import tutorials.classes.C;

public class Foo {

	public int process(B obj) {
		return 1 + obj.getB();
	}

	public int process(C obj) {
		return 2 + obj.getC();
	}

	public int process2(B obj1, A obj2) {
		return 1 + obj1.getB() + obj2.getA();
	}
	
	public int process2(B obj1, C obj2) {
		return 1 - obj1.getB() - obj2.getC();
	}
}
