package org.bitbucket.evl.predicate;

import org.bitbucket.evl.MatchMethodComparator;
import org.bitbucket.evl.MatchMethodItem;
import org.bitbucket.evl.comparators.ProductDistanceComparator;

public class PredicateComparator extends MatchMethodComparator {

	@Override
	public int compare(MatchMethodItem m1, MatchMethodItem m2) {
		
		// We first compare the methods.
		Predicate predicate1 = null;
		if (m1.getData() instanceof Predicate) {
			predicate1 = (Predicate)m1.getData();
			
			// Set the current args.
			predicate1.setArgs(args());
		}
		
		Predicate predicate2 = null;
		if (m2.getData() instanceof Predicate) {
			predicate2 = (Predicate)m2.getData();
			
			// Set the current args.
			predicate2.setArgs(args());
		}
		
		int comparison = 0;
		
		if (predicate1 != null) {
			// We search for the "closest" tuple, so if we want to choose m1 if it has greater boolean value i.e. m1 < m2, 
			// then we need to invert the result. 
			comparison = -predicate1.compareTo(predicate2);
		}
		
		if (comparison == 0 && predicate2 != null) {
			// Highest priority wins, so we let the result because we compare predicate2 < predicate1.
			comparison = predicate2.compareTo(predicate1);
		}
		
		if (comparison == 0) {
			// Equality, compare with symmetric comparison
			return ProductDistanceComparator.compare(m1.getDistanceTuple(), m2.getDistanceTuple());
		}
		
		return comparison;
	}

}
