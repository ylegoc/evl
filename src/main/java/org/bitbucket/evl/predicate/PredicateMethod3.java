package org.bitbucket.evl.predicate;

import java.lang.reflect.Method;
import java.lang.invoke.MethodHandle;
import org.bitbucket.evl.Method3D;
import org.bitbucket.evl.util.EmptyMap;


public class PredicateMethod3<ReturnType> extends Method3D<ReturnType, Method> {
	
	public PredicateMethod3() {
		comparator(new PredicateComparator());
		cache(new EmptyMap<Method3D.ClassTuple, MethodHandle>());
	}
	
}
