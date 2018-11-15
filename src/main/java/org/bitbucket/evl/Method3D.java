package org.bitbucket.evl;

import java.lang.invoke.MethodHandle;
import java.util.Map;

import org.bitbucket.evl.exception.MethodInsertionException;
import org.bitbucket.evl.util.CacheFactory;


public class Method3D<ReturnType, DataType> extends InvokableMethod3D<ReturnType> {
	
	public Method3D<ReturnType, DataType> add(Class<?> classInstance, String name, Class<?>... parameterTypes) {
		return (Method3D<ReturnType, DataType>)super.add(classInstance, name, parameterTypes);
	}
	
	public Method3D<ReturnType, DataType> add(Object object, String name, Class<?>... parameterTypes) {
		return (Method3D<ReturnType, DataType>)super.add(object, name, parameterTypes);
	}
	
	public Method3D<ReturnType, DataType> addAll(Class<?> classInstance, String name) {
		return (Method3D<ReturnType, DataType>)super.addAll(classInstance, name);
	}
	
	public Method3D<ReturnType, DataType> addAll(Object object, String name) {
		return (Method3D<ReturnType, DataType>)super.addAll(object, name);
	}
	
	public Method3D<ReturnType, DataType> data(Comparable<?> data) {
		return (Method3D<ReturnType, DataType>)super.data(data);
	}
	
	public Method3D<ReturnType, DataType> comparator(MethodComparatorD methodComparator) {
		this.methodComparator = methodComparator;
		return this;
	}
	
	public Method3D<ReturnType, DataType> cache(Map<ClassTuple, MethodHandle> cacheMap) {
		this.cache = cacheMap;
		return this;
	}
	
	public Method3D<ReturnType, DataType> unboundedCache() {
		this.cache = CacheFactory.<ClassTuple, MethodHandle>createUnboundedCache();
		return this;
	}
	
	public Method3D<ReturnType, DataType> boundedCache(int capacity) {
		this.cache = CacheFactory.<ClassTuple, MethodHandle>createBoundedCache(capacity);
		return this;
	}

}
