package org.bitbucket.evl.data.predicate;

import java.lang.reflect.Method;

import org.bitbucket.evl.data.DispatchableMethodD;
import org.bitbucket.evl.data.EmptyMap;
import org.bitbucket.evl.data.Method4D;


public class PredicateMethod4<ReturnType> extends Method4D<ReturnType, Method> {
	
	public PredicateMethod4() {
		comparator(new PredicateComparator());
		cache(new EmptyMap<Method4D.ClassTuple, DispatchableMethodD<Method>>());
	}
	
}
