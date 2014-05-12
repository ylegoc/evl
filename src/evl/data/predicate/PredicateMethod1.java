package evl.data.predicate;

import java.lang.reflect.Method;

import evl.data.DispatchableMethodD;
import evl.data.EmptyMap;
import evl.data.Method1D;
import evl.data.Method2D;
import evl.data.Method3D;
import evl.data.Method4D;


public class PredicateMethod1<ReturnType> extends Method1D<ReturnType, Method> {
	
	public PredicateMethod1() {
		comparator(new PredicateComparator());
		cache(new EmptyMap<Class<?>, DispatchableMethodD<Method>>());
	}
	
}
