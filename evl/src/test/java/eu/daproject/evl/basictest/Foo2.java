package eu.daproject.evl.basictest;

public class Foo2 {

	public int foo(IA a, IA b) {
		return 1;
	}
	
	public int foo(D a, IA b) {
		return 2;
	}
	
	public int foo(IA a, D b) {
		return 3;
	}

}
