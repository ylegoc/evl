package org.bitbucket.evl;

import org.bitbucket.evl.comparators.LexicographicDistanceComparator;

public class AsymmetricComparator extends MatchMethodComparator {

	@Override
	public int compare(MatchMethodItem m1, MatchMethodItem m2) {
		int comparison = LexicographicDistanceComparator.compare(m1.getDistanceTuple(), m2.getDistanceTuple());
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
				// We search for the "closest" tuple, so if we want to choose m1 if it has greater priority i.e. m1 < m2, 
				// then we need to invert the result. 
				return -priority1.compareTo(priority2);
			}
			
			if (priority2 != null) {
				// Highest priority wins, so we let the result because we compare prority2 < priority1.
				return priority2.compareTo(priority1);
			}
		}
		
		return comparison;
	}
}
