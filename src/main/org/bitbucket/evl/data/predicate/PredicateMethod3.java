package org.bitbucket.evl.data.predicate;

import java.lang.reflect.Method;

import org.bitbucket.evl.data.DispatchableMethodD;
import org.bitbucket.evl.data.EmptyMap;
import org.bitbucket.evl.data.Method3D;


public class PredicateMethod3<ReturnType> extends Method3D<ReturnType, Method> {
	
	public PredicateMethod3() {
		comparator(new PredicateComparator());
		cache(new EmptyMap<Method3D.ClassTuple, DispatchableMethodD<Method>>());
	}
	
}
