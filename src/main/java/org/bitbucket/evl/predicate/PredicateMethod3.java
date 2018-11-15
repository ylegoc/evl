package org.bitbucket.evl.predicate;

import java.lang.invoke.MethodHandle;

import org.bitbucket.evl.Method3;
import org.bitbucket.evl.util.EmptyMap;


public class PredicateMethod3<ReturnType> extends Method3<ReturnType> {
	
	public PredicateMethod3() {
		comparator(new PredicateComparator());
		cache(new EmptyMap<Method3.ClassTuple, MethodHandle>());
	}
	
}
