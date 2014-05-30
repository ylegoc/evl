package org.bitbucket.evl;

import org.bitbucket.evl.comparators.LexicographicDistanceComparator;

public class AsymmetricComparator extends MethodComparatorD<Void> {

	@Override
	public int compare(MethodItemD<Void> m1, MethodItemD<Void> m2) {
		return LexicographicDistanceComparator.compare(m1.getDistanceTuple(), m2.getDistanceTuple());
	}
}
