package eu.daproject.evl.basictest;

public class Foo {

	public int foo(IA a) {
		return 1;
	}
	
	public int foo(D e) {
		return 2;
	}
	
	public int foo(IC c) {
		return 3;
	}
	
	public int bar(double d) {
		return (int)d;
	}
	
	public static int foo(E e) {
		return 4;
	}
}
