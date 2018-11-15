package org.bitbucket.evl.predicate;

import java.lang.invoke.MethodHandle;

import org.bitbucket.evl.Method1;
import org.bitbucket.evl.util.EmptyMap;


public class PredicateMethod1<ReturnType> extends Method1<ReturnType> {
	
	public PredicateMethod1() {
		comparator(new PredicateComparator());
		cache(new EmptyMap<Class<?>, MethodHandle>());
	}
	
}
