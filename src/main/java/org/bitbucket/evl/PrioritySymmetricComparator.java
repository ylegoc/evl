package org.bitbucket.evl;



public class PrioritySymmetricComparator extends SymmetricComparator {

	@Override
	public int compare(MethodItem m1, MethodItem m2) {
		
		int comparison = super.compare(m1, m2);
		if (comparison == 0) {
			Integer data1 = (Integer)m1.getData();
			Integer data2 = (Integer)m2.getData();
			return data1.compareTo(data2);
		}
		
		return comparison;
	}

}
