package org.bitbucket.evl.test;

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
}
