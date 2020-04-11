package eu.daproject.evl.features.part5;

public class ExtendedClass extends Class {
	
	private int i = 7;
	
	public ExtendedClass() {
		foo.add(this, "foo_");
	}
	
	protected int foo_(B b) {
		return b.a + foo_((A)b) + i;
	}
}

