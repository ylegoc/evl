package org.bitbucket.evl;

import java.lang.invoke.MethodHandle;
import java.util.Map;

import org.bitbucket.evl.util.CacheFactory;


public abstract class InvokableMethod1D<ReturnType, DataType> extends MultiMethodD<ReturnType, DataType> {
	
	protected Map<Class<?>, MethodHandle> cache;
	
	public InvokableMethod1D() {
		super(1, new AsymmetricComparator());
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
	
	public ReturnType invoke(Object arg1, Object arg2, Object arg3) throws Throwable {

		MethodHandle method = cache.get(arg1.getClass());
		
		if (method != null) {	
			return (ReturnType)method.invoke(arg1, arg2, arg3);
		}
		
		return (ReturnType)processAndCache(arg1).invoke(arg1, arg2, arg3);
	}

	public ReturnType invoke(Object arg1, Object arg2, Object arg3, Object arg4) throws Throwable {

		MethodHandle method = cache.get(arg1.getClass());
		
		if (method != null) {	
			return (ReturnType)method.invoke(arg1, arg2, arg3, arg4);
		}
		
		return (ReturnType)processAndCache(arg1).invoke(arg1, arg2, arg3, arg4);
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
		
		return (ReturnType)processAndCache(arg1).invoke(arg1, arg2, arg3, arg4, arg5, arg6);
	}
	
	public ReturnType invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7) throws Throwable {

		MethodHandle method = cache.get(arg1.getClass());
		
		if (method != null) {	
			return (ReturnType)method.invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7);
		}
		
		return (ReturnType)processAndCache(arg1).invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}
}
