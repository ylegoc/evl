package org.bitbucket.evl.predicate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bitbucket.evl.MethodComparatorD;
import org.bitbucket.evl.MethodItemD;
import org.bitbucket.evl.comparators.ProductDistanceComparator;

public class PredicateComparator extends MethodComparatorD {

	@Override
	public int compare(MethodItemD m1, MethodItemD m2) {
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
