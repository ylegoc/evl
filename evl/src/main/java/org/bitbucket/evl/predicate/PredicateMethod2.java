package org.bitbucket.evl.predicate;

import java.lang.invoke.MethodHandle;

import org.bitbucket.evl.Method2;
import org.bitbucket.evl.util.EmptyMap;


public class PredicateMethod2<ReturnType> extends Method2<ReturnType> {
	
	public PredicateMethod2() {
		comparator(new PredicateComparator());
		cache(new EmptyMap<Method2.ClassTuple, MethodHandle>());
	}
	
}
