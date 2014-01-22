package tests.classes;

public class Foo {

	public int foo(IA a) {
		System.out.println("foo IA");
		return 1;
	}
	
	public int foo(D e) {
		System.out.println("foo D");
		return 2;
	}
	
	public int foo(IC c) {
		System.out.println("foo IC");
		return 3;
	}
	
	public int bar(double d) {
		return (int)d;
	}
}
