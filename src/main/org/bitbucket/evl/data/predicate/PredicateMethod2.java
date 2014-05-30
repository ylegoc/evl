package org.bitbucket.evl.data.predicate;

import java.lang.reflect.Method;

import org.bitbucket.evl.data.DispatchableMethodD;
import org.bitbucket.evl.data.EmptyMap;
import org.bitbucket.evl.data.Method2D;


public class PredicateMethod2<ReturnType> extends Method2D<ReturnType, Method> {
	
	public PredicateMethod2() {
		comparator(new PredicateComparator());
		cache(new EmptyMap<Method2D.ClassTuple, DispatchableMethodD<Method>>());
	}
	
}
