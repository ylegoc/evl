package org.bitbucket.evl.predicate;

import java.lang.reflect.Method;

import org.bitbucket.evl.DispatchableMethodD;
import org.bitbucket.evl.Method4D;
import org.bitbucket.evl.util.EmptyMap;


public class PredicateMethod4<ReturnType> extends Method4D<ReturnType, Method> {
	
	public PredicateMethod4() {
		comparator(new PredicateComparator());
		cache(new EmptyMap<Method4D.ClassTuple, DispatchableMethodD<Method>>());
	}
	
}
