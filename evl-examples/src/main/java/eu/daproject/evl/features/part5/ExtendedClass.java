package eu.daproject.evl.features.part5;

public class ExtendedClass extends Class {
	
	private int i = 4;
	
	protected int fooMatch(B b) {
		return b.a * i + fooMatch((A)b);
	}
}

