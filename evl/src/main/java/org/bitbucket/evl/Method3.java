package org.bitbucket.evl;

import java.lang.invoke.MethodHandle;
import java.util.Arrays;
import java.util.Map;

import org.bitbucket.evl.util.CacheFactory;


public class Method3<ReturnType> extends MultiMethod<ReturnType> {
	
	public static class ClassTuple {
		
		private Class<?>[] tuple = new Class<?>[3];
		
		ClassTuple(Class<?> class1, Class<?> class2, Class<?> class3) {
			tuple[0] = class1;
			tuple[1] = class2;
			tuple[2] = class3;
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
	
	public Method3() {
		super(3, new AsymmetricComparator());
		this.cache = CacheFactory.<ClassTuple, MethodHandle>createBoundedCache(1000);
	}
	
	protected void resetCache() {
		cache.clear();
	}
	
	protected MethodHandle processAndCache(Object... args) throws Throwable {
		
		MethodHandle method = processClassTuple(args).getMethod();
		cache.put(new ClassTuple(args[0].getClass(), args[1].getClass(), args[2].getClass()), method);
		return method;
	}
	
	public Method3<ReturnType> add(Class<?> classInstance, String name, Class<?>... parameterTypes) {
		return (Method3<ReturnType>)super.add(classInstance, name, parameterTypes);
	}
	
	public Method3<ReturnType> add(Object object, String name, Class<?>... parameterTypes) {
		return (Method3<ReturnType>)super.add(object, name, parameterTypes);
	}
	
	public Method3<ReturnType> add(Class<?> classInstance, String name) {
		return (Method3<ReturnType>)super.add(classInstance, name);
	}
	
	public Method3<ReturnType> add(Object object, String name) {
		return (Method3<ReturnType>)super.add(object, name);
	}

	public Method3<ReturnType> add(Object object) {
		return (Method3<ReturnType>)super.add(object);
	}
	
	public Method3<ReturnType> add(Cases cases) {
		return (Method3<ReturnType>)super.add(cases);
	}
	
	public Method3<ReturnType> data(Comparable<?> data) {
		return (Method3<ReturnType>)super.data(data);
	}
	
	public Method3<ReturnType> comparator(MatchMethodComparator methodComparator) {
		this.methodComparator = methodComparator;
		return this;
	}
	
	public Method3<ReturnType> cache(Map<ClassTuple, MethodHandle> cacheMap) {
		this.cache = cacheMap;
		return this;
	}
	
	public Method3<ReturnType> unboundedCache() {
		this.cache = CacheFactory.<ClassTuple, MethodHandle>createUnboundedCache();
		return this;
	}
	
	public Method3<ReturnType> boundedCache(int capacity) {
		this.cache = CacheFactory.<ClassTuple, MethodHandle>createBoundedCache(capacity);
		return this;
	}
	
	public ReturnType invoke(Object arg1, Object arg2, Object arg3) throws Throwable {

		MethodHandle method = cache.get(new ClassTuple(arg1.getClass(), arg2.getClass(), arg3.getClass()));
		
		if (method != null) {	
			return (ReturnType)method.invoke(arg1, arg2, arg3);
		}
		
		return (ReturnType)processAndCache(arg1, arg2, arg3).invoke(arg1, arg2, arg3);
	}
	
	public ReturnType invoke(Object arg1, Object arg2, Object arg3, Object arg4) throws Throwable {

		MethodHandle method = cache.get(new ClassTuple(arg1.getClass(), arg2.getClass(), arg3.getClass()));
		
		if (method != null) {	
			return (ReturnType)method.invoke(arg1, arg2, arg3, arg4);
		}
		
		return (ReturnType)processAndCache(arg1, arg2, arg3, arg4).invoke(arg1, arg2, arg3, arg4);
	}
	
	public ReturnType invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) throws Throwable {

		MethodHandle method = cache.get(new ClassTuple(arg1.getClass(), arg2.getClass(), arg3.getClass()));
		
		if (method != null) {	
			return (ReturnType)method.invoke(arg1, arg2, arg3, arg4, arg5);
		}
		
		return (ReturnType)processAndCache(arg1, arg2, arg3, arg4, arg5).invoke(arg1, arg2, arg3, arg4, arg5);
	}
	
	public ReturnType invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6) throws Throwable {

		MethodHandle method = cache.get(new ClassTuple(arg1.getClass(), arg2.getClass(), arg3.getClass()));
		
		if (method != null) {	
			return (ReturnType)method.invoke(arg1, arg2, arg3, arg4, arg5, arg6);
		}
		
		return (ReturnType)processAndCache(arg1, arg2, arg3, arg4, arg5, arg6).invoke(arg1, arg2, arg3, arg4, arg5, arg6);
	}
	
	public ReturnType invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7) throws Throwable {

		MethodHandle method = cache.get(new ClassTuple(arg1.getClass(), arg2.getClass(), arg3.getClass()));
		
		if (method != null) {	
			return (ReturnType)method.invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7);
		}
		
		return (ReturnType)processAndCache(arg1, arg2, arg3, arg4, arg5, arg6, arg7).invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}	

}
