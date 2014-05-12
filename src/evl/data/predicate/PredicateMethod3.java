package evl.data.predicate;

import java.lang.reflect.Method;

import evl.data.DispatchableMethodD;
import evl.data.EmptyMap;
import evl.data.Method1D;
import evl.data.Method2D;
import evl.data.Method3D;
import evl.data.Method4D;


public class PredicateMethod3<ReturnType> extends Method3D<ReturnType, Method> {
	
	public PredicateMethod3() {
		comparator(new PredicateComparator());
		cache(new EmptyMap<Method3D.ClassTuple, DispatchableMethodD<Method>>());
	}
	
}
