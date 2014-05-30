package org.bitbucket.evl.base;

import org.bitbucket.evl.comparators.LexicographicDistanceComparator;
import org.bitbucket.evl.data.MethodComparatorD;
import org.bitbucket.evl.data.MethodItemD;

public class AsymmetricComparator extends MethodComparatorD<Void> {

	@Override
	public int compare(MethodItemD<Void> m1, MethodItemD<Void> m2) {
		return LexicographicDistanceComparator.compare(m1.getDistanceTuple(), m2.getDistanceTuple());
	}
}
