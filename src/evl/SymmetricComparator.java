package evl;

import evl.comparators.ProductDistanceComparator;

public class SymmetricComparator<DataType> implements MethodComparator<DataType> {

	@Override
	public int compare(MethodItem<DataType> m1, MethodItem<DataType> m2) {
		return ProductDistanceComparator.compare(m1.getDistanceTuple(), m2.getDistanceTuple());
	}

}
