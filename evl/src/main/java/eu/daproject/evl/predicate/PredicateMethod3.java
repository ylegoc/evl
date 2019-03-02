package eu.daproject.evl.predicate;

import java.lang.invoke.MethodHandle;

import eu.daproject.evl.Method3;
import eu.daproject.evl.util.ClassTuple;
import eu.daproject.evl.util.EmptyMap;


public class PredicateMethod3<ReturnType> extends Method3<ReturnType> {
	
	public PredicateMethod3() {
		comparator(new PredicateComparator());
		cache(new EmptyMap<ClassTuple, MethodHandle>());
	}
	
}
