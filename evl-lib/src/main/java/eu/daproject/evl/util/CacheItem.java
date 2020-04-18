package eu.daproject.evl.util;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;

/**
 * The class holds the method handle and reflective method for the cache. 
 *
 */
public class CacheItem {

	private MethodHandle methodHandle;
	private Method method;
	
	public CacheItem(MethodHandle methodHandle, Method method) {
		this.methodHandle = methodHandle;
		this.method = method;
	}

	/**
	 * Gets the method handle.
	 * @return the method handle
	 */
	public MethodHandle getMethodHandle() {
		return methodHandle;
	}

	/**
	 * Gets the reflective method.
	 * @return the method
	 */
	public Method getMethod() {
		return method;
	}
	
}
