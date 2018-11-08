package org.bitbucket.evl;

import java.lang.invoke.MethodHandle;
import java.util.Map;

import org.bitbucket.evl.exception.MethodInsertionException;
import org.bitbucket.evl.util.CacheFactory;


public class Method4<ReturnType> extends InvokableMethod4D<ReturnType, Void> {
	
	// throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException, MethodInsertionException
	public Method4<ReturnType> add(Class<?> classInstance, String name, Class<?>[] parameterTypes, Object object) {
		try {
			super.addMethod(classInstance.getMethod(name, parameterTypes), object, null);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new MethodInsertionException();
		}
		return this;
	}
	
	public Method4<ReturnType> add(Class<?> classInstance, String name, Class<?>[] parameterTypes) {
		return add(classInstance, name, parameterTypes, null);
	}
	
	// throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException
	public Method4<ReturnType> addAll(Class<?> classInstance, String name, Object object) {
		super.addMethodFamily(classInstance, name, object);
		return this;
	}
	
	public Method4<ReturnType> addAll(Class<?> classInstance, String name) {
		return addAll(classInstance, name, null);
	}
	
	public Method4<ReturnType> comparator(MethodComparatorD<Void> methodComparator) {
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
