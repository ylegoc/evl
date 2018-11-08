package org.bitbucket.evl.predicate;

import java.lang.reflect.Method;

import java.lang.invoke.MethodHandle;
import org.bitbucket.evl.Method2D;
import org.bitbucket.evl.util.EmptyMap;


public class PredicateMethod2<ReturnType> extends Method2D<ReturnType, Method> {
	
	public PredicateMethod2() {
		comparator(new PredicateComparator());
		cache(new EmptyMap<Method2D.ClassTuple, MethodHandle>());
	}
	
}
