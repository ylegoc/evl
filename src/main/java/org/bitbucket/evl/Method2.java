package org.bitbucket.evl;

import java.lang.invoke.MethodHandle;
import java.util.Map;

import org.bitbucket.evl.exception.MethodInsertionException;
import org.bitbucket.evl.util.CacheFactory;


public class Method2<ReturnType> extends InvokableMethod2D<ReturnType, Void> {
	
	public Method2<ReturnType> add(Class<?> classInstance, String name, Class<?>... parameterTypes) {
		return (Method2<ReturnType>)super.add(classInstance, name, parameterTypes);
	}
	
	public Method2<ReturnType> add(Object object, String name, Class<?>... parameterTypes) {
		return (Method2<ReturnType>)super.add(object, name, parameterTypes);
	}
	
	public Method2<ReturnType> addAll(Class<?> classInstance, String name) {
		return (Method2<ReturnType>)super.addAll(classInstance, name);
	}
	
	public Method2<ReturnType> addAll(Object object, String name) {
		return (Method2<ReturnType>)super.addAll(object, name);
	}
	
	public Method2<ReturnType> comparator(MethodComparatorD<Void> methodComparator) {
		this.methodComparator = methodComparator;
		return this;
	}
	
	public Method2<ReturnType> cache(Map<ClassTuple, MethodHandle> cacheMap) {
		this.cache = cacheMap;
		return this;
	}
	
	public Method2<ReturnType> unboundedCache() {
		this.cache = CacheFactory.<ClassTuple, MethodHandle>createUnboundedCache();
		return this;
	}
	
	public Method2<ReturnType> boundedCache(int capacity) {
		this.cache = CacheFactory.<ClassTuple, MethodHandle>createBoundedCache(capacity);
		return this;
	}
	
}
