package org.bitbucket.evl;

import java.lang.invoke.MethodHandle;
import java.util.Arrays;
import java.util.Map;

import org.bitbucket.evl.util.CacheFactory;


public abstract class InvokableMethod2D<ReturnType, DataType> extends MultiMethodD<ReturnType, DataType> {
	
	public static class ClassTuple {
		
		private Class<?>[] tuple = new Class<?>[2];
		
		ClassTuple(Class<?> class1, Class<?> class2) {
			tuple[0] = class1;
			tuple[1] = class2;
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
	
	public InvokableMethod2D() {
		super(2, new AsymmetricComparatorD<DataType>());
		this.cache = CacheFactory.<ClassTuple, MethodHandle>createUnboundedCache();
	}
	
	protected void resetCache() {
		cache.clear();
	}
	
	protected MethodHandle processAndCache(Object arg1, Object arg2) throws Throwable {
		
		Object[] args = {arg1, arg2};
		MethodHandle method = processClassTuple(args).getMethod();
		cache.put(new ClassTuple(arg1.getClass(), arg2.getClass()), method);
		return method;
	}
	
	public ReturnType invoke(Object arg1, Object arg2) throws Throwable {

		MethodHandle method = cache.get(new ClassTuple(arg1.getClass(), arg2.getClass()));
		
		if (method != null) {	
			return (ReturnType)method.invoke(arg1, arg2);
		}
		
		return (ReturnType)processAndCache(arg1, arg2).invoke(arg1, arg2);
	}
		
}
