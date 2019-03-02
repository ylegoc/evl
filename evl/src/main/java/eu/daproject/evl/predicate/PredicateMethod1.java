package eu.daproject.evl.predicate;

import java.lang.invoke.MethodHandle;

import eu.daproject.evl.Method1;
import eu.daproject.evl.util.EmptyMap;


public class PredicateMethod1<ReturnType> extends Method1<ReturnType> {
	
	public PredicateMethod1() {
		comparator(new PredicateComparator());
		cache(new EmptyMap<Class<?>, MethodHandle>());
	}
	
}
