package org.bitbucket.evl;

import java.lang.invoke.MethodHandle;
import java.util.Map;

import org.bitbucket.evl.util.CacheFactory;
import org.bitbucket.evl.util.ClassTuple;


public class Method2<ReturnType> extends MultiMethod<ReturnType> {
	
	protected Map<ClassTuple, MethodHandle> cache;
	
	public Method2() {
		super(2, new AsymmetricComparator());
		this.cache = CacheFactory.<ClassTuple, MethodHandle>createUnboundedCache();
	}
	
	protected void resetCache() {
		cache.clear();
	}
	
	protected MethodHandle processAndCache(Object... args) throws Throwable {
		
		MethodHandle method = processClassTuple(args).getMethod();
		cache.put(new ClassTuple(args[0].getClass(), args[1].getClass()), method);
		return method;
	}
	
	public Method2<ReturnType> add(Class<?> classInstance, String name, Class<?>... parameterTypes) {
		return (Method2<ReturnType>)super.add(classInstance, name, parameterTypes);
	}
	
	public Method2<ReturnType> add(Object object, String name, Class<?>... parameterTypes) {
		return (Method2<ReturnType>)super.add(object, name, parameterTypes);
	}
	
	public Method2<ReturnType> add(Class<?> classInstance, String name) {
		return (Method2<ReturnType>)super.add(classInstance, name);
	}
	
	public Method2<ReturnType> add(Object object, String name) {
		return (Method2<ReturnType>)super.add(object, name);
	}
	
	public Method2<ReturnType> add(Object object) {
		return (Method2<ReturnType>)super.add(object);
	}
	
	public Method2<ReturnType> add(Cases cases) {
		return (Method2<ReturnType>)super.add(cases);
	}
	
	public Method2<ReturnType> data(Comparable<?> data) {
		return (Method2<ReturnType>)super.data(data);
	}
	
	public Method2<ReturnType> comparator(MatchMethodComparator methodComparator) {
		this.methodComparator = methodComparator;
		return this;
	}
	
	public Method2<ReturnType> cache(Map<ClassTuple, MethodHandle> cacheMap) {
		this.cache = cacheMap;
		return this;
	}
	
	public Method2<ReturnType> unboundedCache() {
		this.cache = CacheFactory.<ClassTuple, MethodHandle>createUnboundedCache();
		return this;
	}
	
	public Method2<ReturnType> boundedCache(int capacity) {
		this.cache = CacheFactory.<ClassTuple, MethodHandle>createBoundedCache(capacity);
		return this;
	}
	
	public ReturnType invoke(Object arg1, Object arg2) throws Throwable {

		MethodHandle method = cache.get(new ClassTuple(arg1.getClass(), arg2.getClass()));
		
		if (method != null) {	
			return (ReturnType)method.invoke(arg1, arg2);
		}
		
		return (ReturnType)processAndCache(arg1, arg2).invoke(arg1, arg2);
	}
	
	public ReturnType invoke(Object arg1, Object arg2, Object arg3) throws Throwable {

		MethodHandle method = cache.get(new ClassTuple(arg1.getClass(), arg2.getClass()));
		
		if (method != null) {	
			return (ReturnType)method.invoke(arg1, arg2, arg3);
		}
		
		return (ReturnType)processAndCache(arg1, arg2, arg3).invoke(arg1, arg2, arg3);
	}

	public ReturnType invoke(Object arg1, Object arg2, Object arg3, Object arg4) throws Throwable {

		MethodHandle method = cache.get(new ClassTuple(arg1.getClass(), arg2.getClass()));
		
		if (method != null) {	
			return (ReturnType)method.invoke(arg1, arg2, arg3, arg4);
		}
		
		return (ReturnType)processAndCache(arg1, arg2, arg3, arg4).invoke(arg1, arg2, arg3, arg4);
	}
	
	public ReturnType invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) throws Throwable {

		MethodHandle method = cache.get(new ClassTuple(arg1.getClass(), arg2.getClass()));
		
		if (method != null) {	
			return (ReturnType)method.invoke(arg1, arg2, arg3, arg4, arg5);
		}
		
		return (ReturnType)processAndCache(arg1, arg2, arg3, arg4, arg5).invoke(arg1, arg2, arg3, arg4, arg5);
	}
	
	public ReturnType invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6) throws Throwable {

		MethodHandle method = cache.get(new ClassTuple(arg1.getClass(), arg2.getClass()));
		
		if (method != null) {	
			return (ReturnType)method.invoke(arg1, arg2, arg3, arg4, arg5, arg6);
		}
		
		return (ReturnType)processAndCache(arg1, arg2, arg3, arg4, arg5, arg6).invoke(arg1, arg2, arg3, arg4, arg5, arg6);
	}
	
	public ReturnType invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7) throws Throwable {

		MethodHandle method = cache.get(new ClassTuple(arg1.getClass(), arg2.getClass()));
		
		if (method != null) {	
			return (ReturnType)method.invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7);
		}
		
		return (ReturnType)processAndCache(arg1, arg2, arg3, arg4, arg5, arg6, arg7).invoke(arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}
}
