package tutorials.classes;


public class B extends A {

	private int b;
	
	public B(int a, int b) {
		super(a);
		this.b = b;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}
	
}
