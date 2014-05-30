package org.bitbucket.evl.data;



public class PrioritySymmetricComparator<DataType extends Comparable<DataType>> extends SymmetricComparatorD<DataType> {

	@Override
	public int compare(MethodItemD<DataType> m1, MethodItemD<DataType> m2) {
		
		int comparison = super.compare(m1, m2);
		if (comparison == 0) {
			return m1.getData().compareTo(m2.getData());
		}
		
		return comparison;
	}

}
