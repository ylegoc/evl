package org.bitbucket.evl;

import java.lang.invoke.MethodHandle;
import java.util.Map;

import org.bitbucket.evl.exception.MethodInsertionException;
import org.bitbucket.evl.util.CacheFactory;


public class Method3<ReturnType> extends InvokableMethod3D<ReturnType> {
	
	public Method3<ReturnType> add(Class<?> classInstance, String name, Class<?>... parameterTypes) {
		return (Method3<ReturnType>)super.add(classInstance, name, parameterTypes);
	}
	
	public Method3<ReturnType> add(Object object, String name, Class<?>... parameterTypes) {
		return (Method3<ReturnType>)super.add(object, name, parameterTypes);
	}
	
	public Method3<ReturnType> addAll(Class<?> classInstance, String name) {
		return (Method3<ReturnType>)super.addAll(classInstance, name);
	}
	
	public Method3<ReturnType> addAll(Object object, String name) {
		return (Method3<ReturnType>)super.addAll(object, name);
	}
	
	public Method3<ReturnType> comparator(MethodComparatorD methodComparator) {
		this.methodComparator = methodComparator;
		return this;
	}
	
	public Method3<ReturnType> cache(Map<ClassTuple, MethodHandle> cacheMap) {
		this.cache = cacheMap;
		return this;
	}
	
	public Method3<ReturnType> unboundedCache() {
		this.cache = CacheFactory.<ClassTuple, MethodHandle>createUnboundedCache();
		return this;
	}
	
	public Method3<ReturnType> boundedCache(int capacity) {
		this.cache = CacheFactory.<ClassTuple, MethodHandle>createBoundedCache(capacity);
		return this;
	}

}
