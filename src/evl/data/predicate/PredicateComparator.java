package evl.data.predicate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import evl.comparators.ProductDistanceComparator;
import evl.data.MethodComparatorD;
import evl.data.MethodItemD;

public class PredicateComparator extends MethodComparatorD<Method> {

	@Override
	public int compare(MethodItemD<Method> m1, MethodItemD<Method> m2) {
		
		//System.out.println("compare " + m1.getClassTuple() + " with " + m2.getClassTuple());
		
		boolean m1Value = false;
		
		try {
			m1Value = (Boolean)m1.getData().invoke(m1.getObject(), getArgs());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// ?
			System.err.println("error when invoking predicate of " + m1.getClassTuple());
		}
		
		//System.out.println("m1 value = " + m1Value);
		
		if (!m1Value) {
			return -1;
		}
		
		boolean m2Value = false;
		
		try {
			m2Value = (Boolean)m2.getData().invoke(m2.getObject(), getArgs());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// ?
			System.err.println("error when invoking predicate of " + m2.getClassTuple());
		}

		//System.out.println("m2 value = " + m2Value);
		
		if (!m2Value) {
			return 1;
		}
		
		// equality, compare with symmetric comparison
		return ProductDistanceComparator.compare(m1.getDistanceTuple(), m2.getDistanceTuple());
	}

}
