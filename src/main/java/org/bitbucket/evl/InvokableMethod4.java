package org.bitbucket.evl;

import java.lang.invoke.MethodHandle;
import java.util.Arrays;
import java.util.Map;

import org.bitbucket.evl.util.CacheFactory;


public abstract class InvokableMethod4<ReturnType> extends MultiMethod<ReturnType> {
	
	public static class ClassTuple {
		
		private Class<?>[] tuple = new Class<?>[4];
		
		ClassTuple(Class<?> class1, Class<?> class2, Class<?> class3, Class<?> class4) {
			tuple[0] = class1;
			tuple[1] = class2;
			tuple[2] = class3;
			tuple[3] = class4;
		}
		
		@Override
		public int hashCode() {
			return Arrays.hashCode(tuple);
		}
		
		@Override
		public boolean equals(Object obj) {
			return Arrays.equals(tuple, ((ClassTuple)obj).tuple);
		}
	}
	
	protected Map<ClassTuple, MethodHandle> cache;
	
	public InvokableMethod4() {
		super(4, new AsymmetricComparator());
		this.cache = CacheFactory.<ClassTuple, MethodHandle>createBoundedCache(10000);
	}
	
	protected void resetCache() {
		cache.clear();
	}
	
	protected MethodHandle processAndCache(Object arg1, Object arg2, Object arg3, Object arg4) throws Throwable {
		
		Object[] args = {arg1, arg2, arg3, arg4};
		MethodHandle method = processClassTuple(args).getMethod();
		cache.put(new ClassTuple(arg1.getClass(), arg2.getClass(), arg3.getClass(), arg4.getClass()), method);
		return method;
	}
	
	public ReturnType invoke(Object arg1, Object arg2, Object arg3, Object arg4) throws Throwable {

		MethodHandle method = cache.get(new ClassTuple(arg1.getClass(), arg2.getClass(), arg3.getClass(), arg4.getClass()));
		
		if (method != null) {	
			return (ReturnType)method.invoke(arg1, arg2, arg3, arg4);
		}
		
		return (ReturnType)processAndCache(arg1, arg2, arg3, arg4).invoke(arg1, arg2, arg3, arg4);
	}
	
	public ReturnType invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) throws Throwable {

		MethodHandle method = cache.get(new ClassTuple(arg1.getClass(), arg2.getClass(), arg3.getClass(), arg4.getClass()));
		
		if (method != null) {	
			return (ReturnType)method.invoke(arg1, arg2, arg3, arg4, arg5);
		}
		
		return (ReturnType)processAndCache(arg1, arg2, arg3, arg4).invoke(arg1, arg2, arg3, arg4, arg5);
	}
	
	public ReturnType invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6) throws Throwable {

		MethodHandle method = cache.get(new ClassTuple(arg1.getClass(), arg2.getClass(), arg3.getClass(), arg4.getClass()));
		
		if (method != null) {	
			return (ReturnType)method.invoke(arg1, arg2, arg3, arg4, arg5, arg6);
		}
		
		return (ReturnType)processAndCache(arg1, arg2, arg3, arg4).invoke(arg1, arg2, arg3, arg4, arg5, arg6);
	}
	
	public ReturnType invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7) throws Throwable {

		MethodHandle method = cache.get(new ClassTuple(arg1.getClass(), arg2.getClass(), arg3.getClass(), arg4.getClass()));
		
		if (method != null) {	
			return (ReturnType)method.invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7);
		}
		
		return (ReturnType)processAndCache(arg1, arg2, arg3, arg4).invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}	
}
