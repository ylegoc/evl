package evl.base;

import evl.comparators.ProductDistanceComparator;
import evl.data.MethodComparatorD;
import evl.data.MethodItemD;

public class SymmetricComparator implements MethodComparatorD<Void> {

	@Override
	public int compare(MethodItemD<Void> m1, MethodItemD<Void> m2) {
		return ProductDistanceComparator.compare(m1.getDistanceTuple(), m2.getDistanceTuple());
	}
}
