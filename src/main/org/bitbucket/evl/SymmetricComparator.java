package org.bitbucket.evl;

import org.bitbucket.evl.comparators.ProductDistanceComparator;

public class SymmetricComparator extends MethodComparatorD<Void> {

	@Override
	public int compare(MethodItemD<Void> m1, MethodItemD<Void> m2) {
		return ProductDistanceComparator.compare(m1.getDistanceTuple(), m2.getDistanceTuple());
	}
}
