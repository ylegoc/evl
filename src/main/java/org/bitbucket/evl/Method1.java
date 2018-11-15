package org.bitbucket.evl;

import java.lang.invoke.MethodHandle;
import java.util.Map;

import org.bitbucket.evl.exception.MethodInsertionException;
import org.bitbucket.evl.util.CacheFactory;


public class Method1<ReturnType> extends InvokableMethod1D<ReturnType> {
	
	public Method1<ReturnType> add(Class<?> classInstance, String name, Class<?>... parameterTypes) {
		return (Method1<ReturnType>)super.add(classInstance, name, parameterTypes);
	}
	
	public Method1<ReturnType> add(Object object, String name, Class<?>... parameterTypes) {
		return (Method1<ReturnType>)super.add(object, name, parameterTypes);
	}
	
	public Method1<ReturnType> addAll(Class<?> classInstance, String name) {
		return (Method1<ReturnType>)super.addAll(classInstance, name);
	}
	
	public Method1<ReturnType> addAll(Object object, String name) {
		return (Method1<ReturnType>)super.addAll(object, name);
	}
	
	public Method1<ReturnType> comparator(MethodComparatorD methodComparator) {
		this.methodComparator = methodComparator;
		return this;
	}
	
	public Method1<ReturnType> cache(Map<Class<?>, MethodHandle> cacheMap) {
		this.cache = cacheMap;
		return this;
	}
	
	public Method1<ReturnType> unboundedCache() {
		this.cache = CacheFactory.<Class<?>, MethodHandle>createUnboundedCache();
		return this;
	}
	
	public Method1<ReturnType> boundedCache(int capacity) {
		this.cache = CacheFactory.<Class<?>, MethodHandle>createBoundedCache(capacity);
		return this;
	}
}
