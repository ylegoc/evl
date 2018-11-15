package org.bitbucket.evl;



public class PrioritySymmetricComparator<DataType extends Comparable<DataType>> extends SymmetricComparatorD<DataType> {

	@Override
	public int compare(MethodItemD<DataType> m1, MethodItemD<DataType> m2) {
		
		int comparison = super.compare(m1, m2);
		if (comparison == 0) {
			Integer data1 = (Integer)m1.getData();
			Integer data2 = (Integer)m2.getData();
			return data1.compareTo(data2);
		}
		
		return comparison;
	}

}
