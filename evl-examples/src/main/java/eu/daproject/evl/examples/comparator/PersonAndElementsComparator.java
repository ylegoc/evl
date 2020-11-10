package eu.daproject.evl.examples.comparator;

import eu.daproject.evl.MethodComparator;
import eu.daproject.evl.MethodItem;
import eu.daproject.evl.comparators.ProductDistanceComparator;

public class PersonAndElementsComparator extends MethodComparator {

	protected int[] getLastDimensions(int[] d) {
		
		int[] result = new int[d.length - 1];
		
		for (int i = 0; i < d.length - 1; ++i) {
			result[i] = d[i + 1];
		}
		
		return result;
	}
	
	protected int compareTuples(int[] d1, int[] d2) {

		// Integer comparison on the first dimension.
		int firstComparison = Integer.compare(d1[0], d2[0]);
		if (firstComparison != 0) {
			return firstComparison;	
		}
		
		// If equality on the first dimension, then product comparison on the last dimensions.
		return ProductDistanceComparator.compare(getLastDimensions(d1), getLastDimensions(d2));
	}
	
	@Override
	public int compare(MethodItem m1, MethodItem m2) {
		return super.compareWithPriority(m1, m2, compareTuples(m1.getDistanceTuple(), m2.getDistanceTuple()));
	}
	
}
