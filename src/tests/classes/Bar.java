package tests.classes;

public class Bar {

	public int bar(IA a, int p) {
		System.out.println("bar IA");
		return p;
	}
	
	public int bar(D e, int p) {
		System.out.println("bar D");
		return p;
	}
	
	public int foo(IC c, int p) {
		System.out.println("bar IC");
		return p;
	}
}
