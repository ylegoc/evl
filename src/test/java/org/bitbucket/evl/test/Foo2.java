package org.bitbucket.evl.test;

public class Foo2 {

	public int foo(IA a, IA b) {
		System.out.println("foo2 IA IA");
		return 1;
	}
	
	public int foo(D a, IA b) {
		System.out.println("foo D IA");
		return 2;
	}
	
	public int foo(IA a, D b) {
		System.out.println("foo IA D");
		return 3;
	}

}
