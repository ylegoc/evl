package eu.daproject.evl.features.part5;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method1;
import eu.daproject.evl.features.part5.Class2.Add;

public class ExtendedClass2 extends Class2 {
	
	public ExtendedClass2() {
		
		foo.add(new Cases() {
			
			int match(Add op, B b) {
				return b.a + 2;
			}
			
			int match(Multiply op, B b) {
				return b.a * 2;
			}
		});
	}
}
