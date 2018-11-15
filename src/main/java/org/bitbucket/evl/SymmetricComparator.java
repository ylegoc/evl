package org.bitbucket.evl;

import org.bitbucket.evl.comparators.ProductDistanceComparator;

public class SymmetricComparator extends MethodComparator {

	@Override
	public int compare(MethodItem m1, MethodItem m2) {
		int comparison = ProductDistanceComparator.compare(m1.getDistanceTuple(), m2.getDistanceTuple());
		if (comparison == 0) {
			Priority priority1 = null;
			if (m1.getData() instanceof Priority) {
				priority1 = (Priority)m1.getData();
			}
			
			Priority priority2 = null;
			if (m2.getData() instanceof Priority) {
				priority2 = (Priority)m2.getData();
			}
			
			if (priority1 != null) {
				return priority1.compareTo(priority2);
			}
			
			if (priority2 != null) {
				return priority2.compareTo(priority1);
			}
		}
		
		return comparison;
	}
}
