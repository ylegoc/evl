package eu.daproject.evl.features.part5;

public class ExtendedClass2 extends Class2 {
	
	int fooMatch(Add op, B b) {
		return b.a + 3;
	}
	
	int fooMatch(Multiply op, B b) {
		return b.a * 3;
	}
}
