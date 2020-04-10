package eu.daproject.evl.features.part5;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method1;

public class ExtendedClass extends Class {
	
	private int i = 7;
	
	public ExtendedClass() {
		
		foo.add(new Cases() {
			
			int match(B b) {
				return b.a + i;
			}
		});
	}
}
