package eu.daproject.evl.predicate;

import java.lang.invoke.MethodHandle;

import eu.daproject.evl.Method2;
import eu.daproject.evl.util.ClassTuple;
import eu.daproject.evl.util.EmptyMap;


public class PredicateMethod2<ReturnType> extends Method2<ReturnType> {
	
	public PredicateMethod2() {
		comparator(new PredicateComparator());
		cache(new EmptyMap<ClassTuple, MethodHandle>());
	}
	
}
