package eu.daproject.evl.features.part5;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method1;

public class ExtendedClass extends Class {
	
	public ExtendedClass() {
		
		foo.add(new Cases() {
			
			int match(B b) {
				return b.a + 1;
			}
		});
	}
}
