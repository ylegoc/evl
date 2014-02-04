package evl.data;

import evl.comparators.LexicographicDistanceComparator;

/*
 * Abstract class because there is no interest in using the class without using Data.
 * Use rather evl.base.AsymmetricComparator
 */
public class AsymmetricComparatorD<DataType> implements MethodComparatorD<DataType> {

	@Override
	public int compare(MethodItemD<DataType> m1, MethodItemD<DataType> m2) {
		return LexicographicDistanceComparator.compare(m1.getDistanceTuple(), m2.getDistanceTuple());
	}

}
