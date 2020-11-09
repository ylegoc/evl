package eu.daproject.evl.examples.comparator;

import eu.daproject.evl.MethodComparator;
import eu.daproject.evl.MethodItem;
import eu.daproject.evl.comparators.LexicographicDistanceComparator;
import eu.daproject.evl.comparators.ProductDistanceComparator;

public class PersonAndElementsComparator extends MethodComparator {

	protected int[] getLasts(int[] d) {
		
		int[] result = new int[d.length - 1];
		
		for (int i = 0; i < d.length - 1; ++i) {
			result[i] = d[i + 1];
		}
		
		return result;
	}
	
	protected int compareTuples(int[] d1, int[] d2) {

		// Lexicographic comparison on the first dimension.
		int[] d1First = new int[] {d1[0]};
		int[] d2First = new int[] {d2[0]};
		
		int firstComparison = LexicographicDistanceComparator.compare(d1First, d2First);
		if (firstComparison != 0) {
			return firstComparison;	
		}
		
		// If equality on the first dimension, then product comparison on the last dimensions.
		return ProductDistanceComparator.compare(getLasts(d1), getLasts(d2));
	}
	
	@Override
	public int compare(MethodItem m1, MethodItem m2) {
		return super.compareWithPriority(m1, m2, compareTuples(m1.getDistanceTuple(), m2.getDistanceTuple()));
	}
	
}
