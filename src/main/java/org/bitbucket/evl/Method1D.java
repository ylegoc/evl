package org.bitbucket.evl;

import java.lang.invoke.MethodHandle;
import java.util.Map;

import org.bitbucket.evl.exception.MethodInsertionException;
import org.bitbucket.evl.util.CacheFactory;


public class Method1D<ReturnType, DataType> extends InvokableMethod1D<ReturnType, DataType> {
	
	public Method1D<ReturnType, DataType> add(Class<?> classInstance, String name, Class<?>... parameterTypes) {
		return (Method1D<ReturnType, DataType>)super.add(classInstance, name, parameterTypes);
	}
	
	public Method1D<ReturnType, DataType> add(Object object, String name, Class<?>... parameterTypes) {
		return (Method1D<ReturnType, DataType>)super.add(object, name, parameterTypes);
	}
	
	public Method1D<ReturnType, DataType> addAll(Class<?> classInstance, String name) {
		return (Method1D<ReturnType, DataType>)super.addAll(classInstance, name);
	}
	
	public Method1D<ReturnType, DataType> addAll(Object object, String name) {
		return (Method1D<ReturnType, DataType>)super.addAll(object, name);
	}
	
	public Method1D<ReturnType, DataType> data(Comparable<?> data) {
		return (Method1D<ReturnType, DataType>)super.data(data);
	}
	
	public Method1D<ReturnType, DataType> comparator(MethodComparatorD methodComparator) {
		this.methodComparator = methodComparator;
		return this;
	}
	
	public Method1D<ReturnType, DataType> cache(Map<Class<?>, MethodHandle> cacheMap) {
		this.cache = cacheMap;
		return this;
	}
	
	public Method1D<ReturnType, DataType> unboundedCache() {
		this.cache = CacheFactory.<Class<?>, MethodHandle>createUnboundedCache();
		return this;
	}
	
	public Method1D<ReturnType, DataType> boundedCache(int capacity) {
		this.cache = CacheFactory.<Class<?>, MethodHandle>createBoundedCache(capacity);
		return this;
	}
}
