/*******************************************************************************
 * Copyright 2019 The EVL authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package eu.daproject.evl;

import java.lang.invoke.MethodHandle;
import java.util.Map;
import java.util.Map.Entry;

import eu.daproject.evl.exception.AmbiguousMethodException;
import eu.daproject.evl.exception.InvocationException;
import eu.daproject.evl.exception.NoMatchingMethodException;
import eu.daproject.evl.util.CacheFactory;
import eu.daproject.evl.util.CacheItem;

/**
 * Class defining a multimethod of dimension 1 where there is 1 "virtual" parameter.
 *
 * @param <ReturnType> the return type
 */
public class Method1<ReturnType> extends MultiMethod<ReturnType> {
	
	protected Map<Class<?>, CacheItem> cache;
	
	/**
	 * Constructs an empty multimethod.
	 */
	public Method1() {
		super(1, new AsymmetricComparator());
		this.cache = CacheFactory.<Class<?>, CacheItem>createUnboundedCache();
	}

	@Override
	public void clearCache() {
		cache.clear();
	}
	
	/**
	 * Prints the cache. Can help for understanding the behavior of the multimethod.
	 */
	public void printCache() {
		for (Entry<Class<?>, CacheItem> e : cache.entrySet()) {
			System.out.println("<" + e.getKey().getName() + "> -> " + e.getValue().getMethod());
		}
	}
	
	/**
	 * Processes the arguments.
	 * @param args the arguments
	 * @return the method handle to apply
	 * @throws Throwable
	 */
	protected MethodHandle processAndCache(Object... args) {
		
		InvokableMethod method = processClassTuple(args);
		MethodHandle methodHandle = method.getMethodHandle();
		cache.put(args[0].getClass(), new CacheItem(methodHandle, method.getMethod()));
		return methodHandle;
	}
	
	@Override
	public Method1<ReturnType> add(Class<?> classInstance, String name, Class<?>... parameterTypes) {
		return (Method1<ReturnType>)super.add(classInstance, name, parameterTypes);
	}
	
	@Override
	public Method1<ReturnType> add(Object object, String name, Class<?>... parameterTypes) {
		return (Method1<ReturnType>)super.add(object, name, parameterTypes);
	}
	
	@Override
	public Method1<ReturnType> add(Class<?> classInstance, String name) {
		return (Method1<ReturnType>)super.add(classInstance, name);
	}
	
	@Override
	public Method1<ReturnType> add(Object object, String name) {
		return (Method1<ReturnType>)super.add(object, name);
	}
	
	@Override
	public Method1<ReturnType> add(Object object) {
		return (Method1<ReturnType>)super.add(object);
	}
	
	@Override
	public Method1<ReturnType> add(Cases cases) {
		return (Method1<ReturnType>)super.add(cases);
	}
	
	@Override
	public Method1<ReturnType> data(Comparable<?> data) {
		return (Method1<ReturnType>)super.data(data);
	}
	
	@Override
	public Method1<ReturnType> comparator(MethodComparator methodComparator) {
		return (Method1<ReturnType>)super.comparator(methodComparator);
	}
	
	@Override
	public Method1<ReturnType> symmetricComparator() {
		return (Method1<ReturnType>)super.symmetricComparator();
	}
	
	@Override
	public Method1<ReturnType> nonVirtualParameterTypes(Class<?>...types) {
		return (Method1<ReturnType>)super.nonVirtualParameterTypes(types);
	}
	
	@Override
	public Method1<ReturnType> returnType(Class<?> type) {
		return (Method1<ReturnType>)super.returnType(type);
	}
	
	/**
	 * Sets a cache. This method has to be called if the standard bounded or unbounded cache are not suitable.
	 * @param cacheMap the new cache map
	 * @return this instance
	 */
	public Method1<ReturnType> cache(Map<Class<?>, CacheItem> cacheMap) {
		this.cache = cacheMap;
		return this;
	}
	
	/**
	 * Sets an unbounded cache.
	 * @return this instance
	 */
	public Method1<ReturnType> unboundedCache() {
		this.cache = CacheFactory.<Class<?>, CacheItem>createUnboundedCache();
		return this;
	}
	
	/**
	 * Sets a bounded cache with a capacity.
	 * @param capacity the capacity
	 * @return this instance
	 */
	public Method1<ReturnType> boundedCache(int capacity) {
		this.cache = CacheFactory.<Class<?>, CacheItem>createBoundedCache(capacity);
		return this;
	}
	
	/**
	 * Checks the class if it is callable without invocation exception.
	 * @param class1 class 1
	 * @throws InvocationException the exception
	 */
	public void check(Class<?> class1) throws InvocationException {
		super.checkClassTuple(class1);
	}
	
	/**
	 * Invokes the multimethod with 1 argument.
	 * @param arg1 argument 1
	 * @return the result of the application of the matching method for the argument 1
	 * @throws NoMatchingMethodException if there is no matching method
	 * @throws AmbiguousMethodException if there are more than one minimum matching method
	 * @throws Throwable if the call of the dispatched method throws an exception
	 */
	public ReturnType invoke(Object arg1) throws Throwable {

		CacheItem cacheItem = cache.get(arg1.getClass());
		
		if (cacheItem != null) {
			return (ReturnType)cacheItem.getMethodHandle().invoke(arg1);
		}
		
		return (ReturnType)processAndCache(arg1).invoke(arg1);
	}
	
	/**
	 * Invokes the multimethod with 2 arguments.
	 * @param arg1 argument 1
	 * @param arg2 argument 2
	 * @return the result of the application of the matching method for the argument 1
	 * @throws NoMatchingMethodException if there is no matching method
	 * @throws AmbiguousMethodException if there are more than one minimum matching method
	 * @throws Throwable if the call of the dispatched method throws an exception
	 */
	public ReturnType invoke(Object arg1, Object arg2) throws Throwable {

		CacheItem cacheItem = cache.get(arg1.getClass());
		
		if (cacheItem != null) {
			return (ReturnType)cacheItem.getMethodHandle().invoke(arg1, arg2);
		}
		
		return (ReturnType)processAndCache(arg1, arg2).invoke(arg1, arg2);
	}
	
	/**
	 * Invokes the multimethod with 3 arguments.
	 * @param arg1 argument 1
	 * @param arg2 argument 2
	 * @param arg3 argument 3
	 * @return the result of the application of the matching method for the argument 1
	 * @throws NoMatchingMethodException if there is no matching method
	 * @throws AmbiguousMethodException if there are more than one minimum matching method
	 * @throws Throwable if the call of the dispatched method throws an exception
	 */
	public ReturnType invoke(Object arg1, Object arg2, Object arg3) throws Throwable {

		CacheItem cacheItem = cache.get(arg1.getClass());
		
		if (cacheItem != null) {
			return (ReturnType)cacheItem.getMethodHandle().invoke(arg1, arg2, arg3);
		}
		
		return (ReturnType)processAndCache(arg1, arg2, arg3).invoke(arg1, arg2, arg3);
	}

	/**
	 * Invokes the multimethod with 4 arguments.
	 * @param arg1 argument 1
	 * @param arg2 argument 2
	 * @param arg3 argument 3
	 * @param arg4 argument 4
	 * @return the result of the application of the matching method for the argument 1
	 * @throws NoMatchingMethodException if there is no matching method
	 * @throws AmbiguousMethodException if there are more than one minimum matching method
	 * @throws Throwable if the call of the dispatched method throws an exception
	 */
	public ReturnType invoke(Object arg1, Object arg2, Object arg3, Object arg4) throws Throwable {

		CacheItem cacheItem = cache.get(arg1.getClass());
		
		if (cacheItem != null) {
			return (ReturnType)cacheItem.getMethodHandle().invoke(arg1, arg2, arg3, arg4);
		}
		
		return (ReturnType)processAndCache(arg1, arg2, arg3, arg4).invoke(arg1, arg2, arg3, arg4);
	}
	
	/**
	 * Invokes the multimethod with 5 arguments.
	 * @param arg1 argument 1
	 * @param arg2 argument 2
	 * @param arg3 argument 3
	 * @param arg4 argument 4
	 * @param arg5 argument 5
	 * @return the result of the application of the matching method for the argument 1
	 * @throws NoMatchingMethodException if there is no matching method
	 * @throws AmbiguousMethodException if there are more than one minimum matching method
	 * @throws Throwable if the call of the dispatched method throws an exception
	 */
	public ReturnType invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) throws Throwable {

		CacheItem cacheItem = cache.get(arg1.getClass());
		
		if (cacheItem != null) {
			return (ReturnType)cacheItem.getMethodHandle().invoke(arg1, arg2, arg3, arg4, arg5);
		}
		
		return (ReturnType)processAndCache(arg1).invoke(arg1, arg2, arg3, arg4, arg5);
	}
	
	/**
	 * Invokes the multimethod with 6 arguments.
	 * @param arg1 argument 1
	 * @param arg2 argument 2
	 * @param arg3 argument 3
	 * @param arg4 argument 4
	 * @param arg5 argument 5
	 * @param arg6 argument 6
	 * @return the result of the application of the matching method for the argument 1
	 * @throws NoMatchingMethodException if there is no matching method
	 * @throws AmbiguousMethodException if there are more than one minimum matching method
	 * @throws Throwable if the call of the dispatched method throws an exception
	 */
	public ReturnType invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6) throws Throwable {

		CacheItem cacheItem = cache.get(arg1.getClass());
		
		if (cacheItem != null) {
			return (ReturnType)cacheItem.getMethodHandle().invoke(arg1, arg2, arg3, arg4, arg5, arg6);
		}
		
		return (ReturnType)processAndCache(arg1, arg2, arg3, arg4, arg5).invoke(arg1, arg2, arg3, arg4, arg5, arg6);
	}
	
	/**
	 * Invokes the multimethod with 7 arguments.
	 * @param arg1 argument 1
	 * @param arg2 argument 2
	 * @param arg3 argument 3
	 * @param arg4 argument 4
	 * @param arg5 argument 5
	 * @param arg6 argument 6
	 * @param arg7 argument 7
	 * @return the result of the application of the matching method for the argument 1
	 * @throws NoMatchingMethodException if there is no matching method
	 * @throws AmbiguousMethodException if there are more than one minimum matching method
	 * @throws Throwable if the call of the dispatched method throws an exception
	 */
	public ReturnType invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7) throws Throwable {

		CacheItem cacheItem = cache.get(arg1.getClass());
		
		if (cacheItem != null) {
			return (ReturnType)cacheItem.getMethodHandle().invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7);
		}
		
		return (ReturnType)processAndCache(arg1, arg2, arg3, arg4, arg5, arg6, arg7).invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}
}
