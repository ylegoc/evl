package tutorials.tutorial8;

import tutorials.classes.A;
import tutorials.classes.B;

public class Foo {

	public int process(A obj, int x) {
		return 1;
	}
	
	public boolean test(A obj, int x) {
		return (x < 0);
	}

	public int process(B obj, int x) {
		return 2;
	}
	
	public boolean test(B obj, int x) {
		return (x >= 10 && x < 20);
	}
	
	public int process2(B obj, int x) {
		return 3;
	}
	
	public boolean test2(B obj, int x) {
		return (x >= 20);
	}

}
