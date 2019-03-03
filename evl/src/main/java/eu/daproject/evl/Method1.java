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

import eu.daproject.evl.util.CacheFactory;


public class Method1<ReturnType> extends MultiMethod<ReturnType> {
	
	protected Map<Class<?>, MethodHandle> cache;
	
	public Method1() {
		super(1, new AsymmetricComparator());
		this.cache = CacheFactory.<Class<?>, MethodHandle>createUnboundedCache();
	}
	
	public void resetCache() {
		cache.clear();
	}
	
	public void printCache() {
		for (Entry<Class<?>, MethodHandle> e : cache.entrySet()) {
			System.out.println("<" + e.getKey().getName() + "> -> " + e.getValue());
		}
	}
	
	protected MethodHandle processAndCache(Object... args) throws Throwable {
		
		MethodHandle method = processClassTuple(args).getMethod();
		cache.put(args[0].getClass(), method);
		return method;
	}
	
	public Method1<ReturnType> add(Class<?> classInstance, String name, Class<?>... parameterTypes) {
		return (Method1<ReturnType>)super.add(classInstance, name, parameterTypes);
	}
	
	public Method1<ReturnType> add(Object object, String name, Class<?>... parameterTypes) {
		return (Method1<ReturnType>)super.add(object, name, parameterTypes);
	}
	
	public Method1<ReturnType> add(Class<?> classInstance, String name) {
		return (Method1<ReturnType>)super.add(classInstance, name);
	}
	
	public Method1<ReturnType> add(Object object, String name) {
		return (Method1<ReturnType>)super.add(object, name);
	}
	
	public Method1<ReturnType> add(Object object) {
		return (Method1<ReturnType>)super.add(object);
	}
	
	public Method1<ReturnType> add(Cases cases) {
		return (Method1<ReturnType>)super.add(cases);
	}
	
	public Method1<ReturnType> data(Comparable<?> data) {
		return (Method1<ReturnType>)super.data(data);
	}
	
	public Method1<ReturnType> comparator(MethodComparator methodComparator) {
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
	
	public ReturnType invoke(Object arg1) throws Throwable {

		MethodHandle method = cache.get(arg1.getClass());
		
		if (method != null) {	
			return (ReturnType)method.invoke(arg1);
		}
		
		return (ReturnType)processAndCache(arg1).invoke(arg1);
	}
	
	public ReturnType invoke(Object arg1, Object arg2) throws Throwable {

		MethodHandle method = cache.get(arg1.getClass());
		
		if (method != null) {	
			return (ReturnType)method.invoke(arg1, arg2);
		}
		
		return (ReturnType)processAndCache(arg1, arg2).invoke(arg1, arg2);
	}
	
	public ReturnType invoke(Object arg1, Object arg2, Object arg3) throws Throwable {

		MethodHandle method = cache.get(arg1.getClass());
		
		if (method != null) {	
			return (ReturnType)method.invoke(arg1, arg2, arg3);
		}
		
		return (ReturnType)processAndCache(arg1, arg2, arg3).invoke(arg1, arg2, arg3);
	}

	public ReturnType invoke(Object arg1, Object arg2, Object arg3, Object arg4) throws Throwable {

		MethodHandle method = cache.get(arg1.getClass());
		
		if (method != null) {	
			return (ReturnType)method.invoke(arg1, arg2, arg3, arg4);
		}
		
		return (ReturnType)processAndCache(arg1, arg2, arg3, arg4).invoke(arg1, arg2, arg3, arg4);
	}
	
	public ReturnType invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) throws Throwable {

		MethodHandle method = cache.get(arg1.getClass());
		
		if (method != null) {	
			return (ReturnType)method.invoke(arg1, arg2, arg3, arg4, arg5);
		}
		
		return (ReturnType)processAndCache(arg1).invoke(arg1, arg2, arg3, arg4, arg5);
	}
	
	public ReturnType invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6) throws Throwable {

		MethodHandle method = cache.get(arg1.getClass());
		
		if (method != null) {	
			return (ReturnType)method.invoke(arg1, arg2, arg3, arg4, arg5, arg6);
		}
		
		return (ReturnType)processAndCache(arg1, arg2, arg3, arg4, arg5).invoke(arg1, arg2, arg3, arg4, arg5, arg6);
	}
	
	public ReturnType invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7) throws Throwable {

		MethodHandle method = cache.get(arg1.getClass());
		
		if (method != null) {	
			return (ReturnType)method.invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7);
		}
		
		return (ReturnType)processAndCache(arg1, arg2, arg3, arg4, arg5, arg6, arg7).invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}
}
