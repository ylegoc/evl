package org.bitbucket.evl;

import java.lang.invoke.MethodHandle;
import java.util.Map;

import org.bitbucket.evl.util.CacheFactory;


public class Method4<ReturnType> extends InvokableMethod4<ReturnType> {
	
	public Method4<ReturnType> add(Class<?> classInstance, String name, Class<?>... parameterTypes) {
		return (Method4<ReturnType>)super.add(classInstance, name, parameterTypes);
	}
	
	public Method4<ReturnType> add(Object object, String name, Class<?>... parameterTypes) {
		return (Method4<ReturnType>)super.add(object, name, parameterTypes);
	}
	
	public Method4<ReturnType> addAll(Class<?> classInstance, String name) {
		return (Method4<ReturnType>)super.addAll(classInstance, name);
	}
	
	public Method4<ReturnType> addAll(Object object, String name) {
		return (Method4<ReturnType>)super.addAll(object, name);
	}

	public Method4<ReturnType> data(Comparable<?> data) {
		return (Method4<ReturnType>)super.data(data);
	}
	
	public Method4<ReturnType> comparator(MethodComparator methodComparator) {
		this.methodComparator = methodComparator;
		return this;
	}
	
	public Method4<ReturnType> cache(Map<ClassTuple, MethodHandle> cacheMap) {
		this.cache = cacheMap;
		return this;
	}
	
	public Method4<ReturnType> unboundedCache() {
		this.cache = CacheFactory.<ClassTuple, MethodHandle>createUnboundedCache();
		return this;
	}
	
	public Method4<ReturnType> boundedCache(int capacity) {
		this.cache = CacheFactory.<ClassTuple, MethodHandle>createBoundedCache(capacity);
		return this;
	}
	
}
