package eu.daproject.evl.predicate;

import java.lang.invoke.MethodHandle;

import eu.daproject.evl.Method4;
import eu.daproject.evl.util.ClassTuple;
import eu.daproject.evl.util.EmptyMap;


public class PredicateMethod4<ReturnType> extends Method4<ReturnType> {
	
	public PredicateMethod4() {
		comparator(new PredicateComparator());
		cache(new EmptyMap<ClassTuple, MethodHandle>());
	}
	
}
