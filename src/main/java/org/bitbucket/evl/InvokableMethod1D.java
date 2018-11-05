package org.bitbucket.evl;

import java.util.Map;

import org.bitbucket.evl.util.CacheFactory;


public abstract class InvokableMethod1D<ReturnType, DataType> extends MultiMethodD<ReturnType, DataType> {
	
	protected Map<Class<?>, DispatchableMethodD<DataType>> cache;
	
	public InvokableMethod1D() {
		super(1, new AsymmetricComparatorD<DataType>());
		this.cache = CacheFactory.<Class<?>, DispatchableMethodD<DataType>>createUnboundedCache();
	}
	
	protected void resetCache() {
		cache.clear();
	}
	
	public ReturnType invoke(Object arg1) throws Throwable {

		// Get the method handle from the cache.
		DispatchableMethodD<DataType> method = cache.get(arg1.getClass());
		
		// Invoke it if it is in the cache.
		if (method != null) {	
			return (ReturnType)method.getMethod().invoke(arg1);
		}
		
		// Calculate the invoked method and put it in the cache.
		Object[] args = {arg1};
		method = processClassTuple(args);
		cache.put(arg1.getClass(), method);
		
		// Invoke the method.
		return (ReturnType)method.getMethod().invoke(arg1);
	}
	
	public ReturnType invoke(Object arg1, Object arg2) throws Throwable {

		// Get the method handle from the cache.
		DispatchableMethodD<DataType> method = cache.get(arg1.getClass());
		
		// Invoke it if it is in the cache.
		if (method != null) {	
			return (ReturnType)method.getMethod().invoke(arg1, arg2);
		}
		
		// Calculate the invoked method and put it in the cache.
		Object[] args = {arg1};
		method = processClassTuple(args);
		cache.put(arg1.getClass(), method);
		
		// Invoke the method.
		return (ReturnType)method.getMethod().invoke(arg1, arg2);
	}
		
}
