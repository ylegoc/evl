package evl.data.predicate;

import java.lang.reflect.Method;

import evl.data.DispatchableMethodD;
import evl.data.EmptyMap;
import evl.data.Method1D;
import evl.data.Method2D;
import evl.data.Method3D;
import evl.data.Method4D;


public class Predicate<ReturnType> {
	
	public static <ReturnType> Method1D.Builder<ReturnType, Method> method1Builder() {
		return new Method1D.Builder<ReturnType, Method>()
						.comparator(new PredicateComparator())
						.cache(new EmptyMap<Class<?>, DispatchableMethodD<Method>>());
	}
	
	public static <ReturnType> Method2D.Builder<ReturnType, Method> method2Builder() {
		return new Method2D.Builder<ReturnType, Method>()
						.comparator(new PredicateComparator())
						.cache(new EmptyMap<Method2D.ClassTuple, DispatchableMethodD<Method>>());
	}
	
	public static <ReturnType> Method3D.Builder<ReturnType, Method> method3Builder() {
		return new Method3D.Builder<ReturnType, Method>()
						.comparator(new PredicateComparator())
						.cache(new EmptyMap<Method3D.ClassTuple, DispatchableMethodD<Method>>());
	}
	
	public static <ReturnType> Method4D.Builder<ReturnType, Method> method4Builder() {
		return new Method4D.Builder<ReturnType, Method>()
						.comparator(new PredicateComparator())
						.cache(new EmptyMap<Method4D.ClassTuple, DispatchableMethodD<Method>>());
	}
	
}
