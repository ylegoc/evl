package tests.proto;


public class MyMethod extends MMethod {

	public MyMethod() {
		//showMethods();
	}
	
	public int invoke(double v) {
		return 2;
	}
	
	public static void main(String[] args) {
		
		MyMethod m = new MyMethod();
	}
}
