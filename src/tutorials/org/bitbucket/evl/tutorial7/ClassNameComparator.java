package org.bitbucket.evl.tutorial7;

import org.bitbucket.evl.MethodComparator;
import org.bitbucket.evl.MethodItem;

public class ClassNameComparator extends MethodComparator {

	@Override
	public int compare(MethodItem m1, MethodItem m2) {
		
		int length = m1.getClassTuple().get().length;
		String name1 = "";
		String name2 = "";
		
		for (int i = 0; i < length; ++i) {
			name1 += m1.getClassTuple().get()[i].getName() + ";";
			name2 += m2.getClassTuple().get()[i].getName() + ";";
		}
		
		int result = name1.compareTo(name2);
		System.out.println("compare " + name1 + " to " + name2);
		
		return result;
	}

}
