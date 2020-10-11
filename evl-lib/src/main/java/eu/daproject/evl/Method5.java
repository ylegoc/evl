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
import eu.daproject.evl.util.ClassTuple;

/**
 * Class defining a multimethod of dimension 5 where there are 5 "virtual" parameters.
 *
 * @param <ReturnType> the return type
 */
public class Method5<ReturnType> extends MultiMethod<ReturnType> {
	
	protected Map<ClassTuple, CacheItem> cache;
	
	/**
	 * Constructs an empty multimethod.
	 */
	public Method5() {
		super(4, new AsymmetricComparator());
		this.cache = CacheFactory.<ClassTuple, CacheItem>createBoundedCache(10000);
	}
	
	@Override
	public void clearCache() {
		cache.clear();
	}
	
	/**
	 * Prints the cache. Can help for understanding the behavior of the multimethod.
	 */
	public void printCache() {
		for (Entry<ClassTuple, CacheItem> e : cache.entrySet()) {
			System.out.println(e.getKey() + " -> " + e.getValue().getMethod());
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
		cache.put(new ClassTuple(args[0].getClass(), args[1].getClass(), args[2].getClass(), args[3].getClass(), args[4].getClass()), new CacheItem(methodHandle, method.getMethod()));
		return methodHandle;
	}
	
	@Override
	public Method5<ReturnType> add(Class<?> classInstance, String name, Class<?>... parameterTypes) {
		return (Method5<ReturnType>)super.add(classInstance, name, parameterTypes);
	}
	
	@Override
	public Method5<ReturnType> add(Object object, String name, Class<?>... parameterTypes) {
		return (Method5<ReturnType>)super.add(object, name, parameterTypes);
	}
	
	@Override
	public Method5<ReturnType> add(Class<?> classInstance, String name) {
		return (Method5<ReturnType>)super.add(classInstance, name);
	}
	
	@Override
	public Method5<ReturnType> add(Object object, String name) {
		return (Method5<ReturnType>)super.add(object, name);
	}
	
	@Override
	public Method5<ReturnType> add(Object object) {
		return (Method5<ReturnType>)super.add(object);
	}
	
	@Override
	public Method5<ReturnType> add(Cases cases) {
		return (Method5<ReturnType>)super.add(cases);
	}

	@Override
	public Method5<ReturnType> data(Comparable<?> data) {
		return (Method5<ReturnType>)super.data(data);
	}
	
	@Override
	public Method5<ReturnType> comparator(MethodComparator methodComparator) {
		return (Method5<ReturnType>)super.comparator(methodComparator);
	}
	
	@Override
	public Method5<ReturnType> symmetricComparator() {
		return (Method5<ReturnType>)super.symmetricComparator();
	}
	
	@Override
	public Method5<ReturnType> nonVirtualParameterTypes(Class<?>...types) {
		return (Method5<ReturnType>)super.nonVirtualParameterTypes(types);
	}
	
	@Override
	public Method5<ReturnType> returnType(Class<?> type) {
		return (Method5<ReturnType>)super.returnType(type);
	}
	
	@Override
	public Method5<ReturnType> notOverridable() {
		return (Method5<ReturnType>)super.notOverridable();
	}
	
	/**
	 * Sets a cache. This method has to be called if the standard bounded or unbounded cache are not suitable.
	 * @param cacheMap the new cache map
	 * @return this instance
	 */
	public Method5<ReturnType> cache(Map<ClassTuple, CacheItem> cacheMap) {
		this.cache = cacheMap;
		return this;
	}
	
	/**
	 * Sets an unbounded cache.
	 * @return this instance
	 */
	public Method5<ReturnType> unboundedCache() {
		this.cache = CacheFactory.<ClassTuple, CacheItem>createUnboundedCache();
		return this;
	}
	
	/**
	 * Sets a bounded cache with a capacity.
	 * @param capacity the capacity
	 * @return this instance
	 */
	public Method5<ReturnType> boundedCache(int capacity) {
		this.cache = CacheFactory.<ClassTuple, CacheItem>createBoundedCache(capacity);
		return this;
	}
	
	/**
	 * Checks the classes if they are callable without invocation exception.
	 * @param class1 class 1
	 * @param class2 class 2
	 * @param class3 class 3
	 * @param class4 class 4
	 * @param class5 class 5
	 * @throws InvocationException the exception
	 */
	public void check(Class<?> class1, Class<?> class2, Class<?> class3, Class<?> class4, Class<?> class5) throws InvocationException {
		super.checkClassTuple(class1, class2, class3, class4, class5);
	}
	
	/**
	 * Invokes the multimethod with 5 arguments.
	 * @param arg1 argument 1
	 * @param arg2 argument 2
	 * @param arg3 argument 3
	 * @param arg4 argument 4
	 * @param arg5 argument 5
	 * @return the result of the application of the matching method for the arguments 1, 2, 3 and 4
	 * @throws NoMatchingMethodException if there is no matching method
	 * @throws AmbiguousMethodException if there are more than one minimum matching method
	 * @throws Throwable if the call of the dispatched method throws an exception
	 */
	public ReturnType invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) throws Throwable {

		CacheItem cacheItem = cache.get(new ClassTuple(arg1.getClass(), arg2.getClass(), arg3.getClass(), arg4.getClass(), arg5.getClass()));
		
		if (cacheItem != null) {
			return (ReturnType)cacheItem.getMethodHandle().invoke(arg1, arg2, arg3, arg4, arg5);
		}
		
		return (ReturnType)processAndCache(arg1, arg2, arg3, arg4, arg5).invoke(arg1, arg2, arg3, arg4, arg5);
	}
	
	/**
	 * Invokes the multimethod with 6 arguments.
	 * @param arg1 argument 1
	 * @param arg2 argument 2
	 * @param arg3 argument 3
	 * @param arg4 argument 4
	 * @param arg5 argument 5
	 * @param arg6 argument 6
	 * @return the result of the application of the matching method for the arguments 1, 2, 3 and 4
	 * @throws NoMatchingMethodException if there is no matching method
	 * @throws AmbiguousMethodException if there are more than one minimum matching method
	 * @throws Throwable if the call of the dispatched method throws an exception
	 */
	public ReturnType invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6) throws Throwable {

		CacheItem cacheItem = cache.get(new ClassTuple(arg1.getClass(), arg2.getClass(), arg3.getClass(), arg4.getClass(), arg5.getClass()));
		
		if (cacheItem != null) {
			return (ReturnType)cacheItem.getMethodHandle().invoke(arg1, arg2, arg3, arg4, arg5, arg6);
		}
		
		return (ReturnType)processAndCache(arg1, arg2, arg3, arg4, arg5, arg6).invoke(arg1, arg2, arg3, arg4, arg5, arg6);
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
	 * @return the result of the application of the matching method for the arguments 1, 2, 3 and 4
	 * @throws NoMatchingMethodException if there is no matching method
	 * @throws AmbiguousMethodException if there are more than one minimum matching method
	 * @throws Throwable if the call of the dispatched method throws an exception
	 */
	public ReturnType invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7) throws Throwable {

		CacheItem cacheItem = cache.get(new ClassTuple(arg1.getClass(), arg2.getClass(), arg3.getClass(), arg4.getClass(), arg5.getClass()));
		
		if (cacheItem != null) {
			return (ReturnType)cacheItem.getMethodHandle().invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7);
		}
		
		return (ReturnType)processAndCache(arg1, arg2, arg3, arg4, arg5, arg6, arg7).invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}	
	
}
