package eu.daproject.evl.features.part5;

public class ExtendedClass extends Class {
	
	private int i = 7;
	
	public ExtendedClass() {
		// No need to add foo_, it is already done in Class()
	}
	
	protected int foo_(B b) {
		return b.a + foo_((A)b) + i;
	}
}

