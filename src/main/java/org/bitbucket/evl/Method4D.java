package org.bitbucket.evl;

import java.lang.invoke.MethodHandle;
import java.util.Map;

import org.bitbucket.evl.exception.MethodInsertionException;
import org.bitbucket.evl.util.CacheFactory;


public class Method4D<ReturnType, DataType> extends InvokableMethod4D<ReturnType, DataType> {
	
	public Method4D<ReturnType, DataType> add(Class<?> classInstance, String name, Class<?>... parameterTypes) {
		return (Method4D<ReturnType, DataType>)super.add(classInstance, name, parameterTypes);
	}
	
	public Method4D<ReturnType, DataType> add(Object object, String name, Class<?>... parameterTypes) {
		return (Method4D<ReturnType, DataType>)super.add(object, name, parameterTypes);
	}
	
	public Method4D<ReturnType, DataType> addAll(Class<?> classInstance, String name) {
		return (Method4D<ReturnType, DataType>)super.addAll(classInstance, name);
	}
	
	public Method4D<ReturnType, DataType> addAll(Object object, String name) {
		return (Method4D<ReturnType, DataType>)super.addAll(object, name);
	}
	
	public Method4D<ReturnType, DataType> data(Comparable<?> data) {
		return (Method4D<ReturnType, DataType>)super.data(data);
	}

	public Method4D<ReturnType, DataType> comparator(MethodComparatorD methodComparator) {
		this.methodComparator = methodComparator;
		return this;
	}
	
	public Method4D<ReturnType, DataType> cache(Map<ClassTuple, MethodHandle> cacheMap) {
		this.cache = cacheMap;
		return this;
	}
	
	public Method4D<ReturnType, DataType> unboundedCache() {
		this.cache = CacheFactory.<ClassTuple, MethodHandle>createUnboundedCache();
		return this;
	}
	
	public Method4D<ReturnType, DataType> boundedCache(int capacity) {
		this.cache = CacheFactory.<ClassTuple, MethodHandle>createBoundedCache(capacity);
		return this;
	}
	
}
