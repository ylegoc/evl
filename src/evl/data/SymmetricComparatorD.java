package evl.data;

import evl.comparators.ProductDistanceComparator;

/*
 * Abstract class because there is no interest in using the class without using Data.
 * Use rather evl.base.SymmetricComparator
 */
public class SymmetricComparatorD<DataType> implements MethodComparatorD<DataType> {

	@Override
	public int compare(MethodItemD<DataType> m1, MethodItemD<DataType> m2) {
		return ProductDistanceComparator.compare(m1.getDistanceTuple(), m2.getDistanceTuple());
	}

}
