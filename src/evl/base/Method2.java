package evl.base;

import java.util.Map;

import evl.data.BaseMethod2D;
import evl.data.DispatchableMethodD;
import evl.data.MethodComparatorD;
import evl.exceptions.MethodInsertionException;
import evl.util.CacheFactory;


public class Method2<ReturnType> extends BaseMethod2D<ReturnType, Void> {
	
	// throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException, MethodInsertionException
	public Method2<ReturnType> add(Class<?> classInstance, String name, Class<?>[] parameterTypes, Object object) {
		try {
			super.addMethod(classInstance.getMethod(name, parameterTypes), object, null);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new MethodInsertionException();
		}
		return this;
	}
	
	// throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException
	public Method2<ReturnType> addAll(Class<?> classInstance, String name, Object object) {
		super.addMethodFamily(classInstance, name, object);
		return this;
	}
	
	public Method2<ReturnType> comparator(MethodComparatorD<Void> methodComparator) {
		this.methodComparator = methodComparator;
		return this;
	}
	
	public Method2<ReturnType> cache(Map<ClassTuple, DispatchableMethodD<Void>> cacheMap) {
		this.cache = cacheMap;
		return this;
	}
	
	public Method2<ReturnType> unboundedCache() {
		this.cache = CacheFactory.<ClassTuple, DispatchableMethodD<Void>>createUnboundedCache();
		return this;
	}
	
	public Method2<ReturnType> boundedCache(long capacity) {
		this.cache = CacheFactory.<ClassTuple, DispatchableMethodD<Void>>createBoundedCache(capacity);
		return this;
	}
	
}
