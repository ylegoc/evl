package org.bitbucket.evl.predicate;

import java.lang.invoke.MethodHandle;

import org.bitbucket.evl.Method4;
import org.bitbucket.evl.util.EmptyMap;


public class PredicateMethod4<ReturnType> extends Method4<ReturnType> {
	
	public PredicateMethod4() {
		comparator(new PredicateComparator());
		cache(new EmptyMap<Method4.ClassTuple, MethodHandle>());
	}
	
}
