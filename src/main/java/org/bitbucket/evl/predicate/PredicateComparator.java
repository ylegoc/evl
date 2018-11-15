package org.bitbucket.evl.predicate;

import org.bitbucket.evl.MethodComparator;
import org.bitbucket.evl.MethodItem;
import org.bitbucket.evl.comparators.ProductDistanceComparator;

public class PredicateComparator extends MethodComparator {

	@Override
	public int compare(MethodItem m1, MethodItem m2) {
		/*
		boolean m2Value = false;
		
		try {
			Method m2Method = (Method)m2.getData();
			m2Value = (Boolean)m2Method.invoke(m2.getObject(), getArgs());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// ?
			System.err.println("error when invoking predicate of " + m2.getClassTuple());
		}
		
		if (!m2Value) {
			return -1;
		}
		
		boolean m1Value = false;
		
		try {
			m1Value = (Boolean)m1.getData().invoke(m1.getObject(), getArgs());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// ?
			System.err.println("error when invoking predicate of " + m1.getClassTuple());
		}
		
		if (!m1Value) {
			return 1;
		}*/
		
		// equality, compare with symmetric comparison
		return ProductDistanceComparator.compare(m1.getDistanceTuple(), m2.getDistanceTuple());
	}

}
