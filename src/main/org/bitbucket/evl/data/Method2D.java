package org.bitbucket.evl.data;

import java.util.Map;

import org.bitbucket.evl.exceptions.MethodInsertionException;
import org.bitbucket.evl.util.CacheFactory;


public class Method2D<ReturnType, DataType> extends InvokableMethod2D<ReturnType, DataType> {
	
	// throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException, MethodInsertionException
	public Method2D<ReturnType, DataType> add(Class<?> classInstance, String name, Class<?>[] parameterTypes, Object object, DataType data) {
		try {
			super.addMethod(classInstance.getMethod(name, parameterTypes), object, data);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new MethodInsertionException();
		}
		return this;
	}
	
	// throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException, MethodInsertionException
	public Method2D<ReturnType, DataType> add(Class<?> classInstance, String name, Class<?>[] parameterTypes, DataType data) {
		return add(classInstance, name, parameterTypes, null, data);
	}
	
	public Method2D<ReturnType, DataType> comparator(MethodComparatorD<DataType> methodComparator) {
		this.methodComparator = methodComparator;
		return this;
	}
	
	public Method2D<ReturnType, DataType> cache(Map<ClassTuple, DispatchableMethodD<DataType>> cacheMap) {
		this.cache = cacheMap;
		return this;
	}
	
	public Method2D<ReturnType, DataType> unboundedCache() {
		this.cache = CacheFactory.<ClassTuple, DispatchableMethodD<DataType>>createUnboundedCache();
		return this;
	}
	
	public Method2D<ReturnType, DataType> boundedCache(long capacity) {
		this.cache = CacheFactory.<ClassTuple, DispatchableMethodD<DataType>>createBoundedCache(capacity);
		return this;
	}

}
