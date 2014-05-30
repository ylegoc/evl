package org.bitbucket.evl.predicate;

import java.lang.reflect.Method;

import org.bitbucket.evl.DispatchableMethodD;
import org.bitbucket.evl.Method1D;
import org.bitbucket.evl.util.EmptyMap;


public class PredicateMethod1<ReturnType> extends Method1D<ReturnType, Method> {
	
	public PredicateMethod1() {
		comparator(new PredicateComparator());
		cache(new EmptyMap<Class<?>, DispatchableMethodD<Method>>());
	}
	
}
