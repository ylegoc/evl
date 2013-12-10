package evl;

import evl.comparators.LexicographicDistanceComparator;

public class AsymmetricComparator<DataType> implements MethodComparator<DataType> {

	@Override
	public int compare(MethodItem<DataType> m1, MethodItem<DataType> m2) {
		return LexicographicDistanceComparator.compare(m1.getDistanceTuple(), m2.getDistanceTuple());
	}

}
