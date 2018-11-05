package org.bitbucket.evl;

import java.lang.invoke.MethodHandle;
import java.util.Map;

import org.bitbucket.evl.util.CacheFactory;


public abstract class InvokableMethod1D<ReturnType, DataType> extends MultiMethodD<ReturnType, DataType> {
	
	protected Map<Class<?>, MethodHandle> cache;
	
	public InvokableMethod1D() {
		super(1, new AsymmetricComparatorD<DataType>());
		this.cache = CacheFactory.<Class<?>, MethodHandle>createUnboundedCache();
	}
	
	protected void resetCache() {
		cache.clear();
	}
	
	protected MethodHandle processAndCache(Object arg1) throws Throwable {
		
		Object[] args = {arg1};
		MethodHandle method = processClassTuple(args).getMethod();
		cache.put(arg1.getClass(), method);
		return method;
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
		
		return (ReturnType)processAndCache(arg1).invoke(arg1, arg2);
	}
	
	// ... continue with 8 arguments
}
