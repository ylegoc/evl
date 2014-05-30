package org.bitbucket.evl;

import java.util.Map;

import org.bitbucket.evl.exception.InvocationException;
import org.bitbucket.evl.util.CacheFactory;


public abstract class InvokableMethod1D<ReturnType, DataType> extends MultiMethodD<ReturnType, DataType> {
	
	protected Map<Class<?>, DispatchableMethodD<DataType>> cache;
	
	public InvokableMethod1D() {
		super(1, new AsymmetricComparatorD<DataType>());
		this.cache = CacheFactory.<Class<?>, DispatchableMethodD<DataType>>createUnboundedCache();;
	}
	
	protected void resetCache() {
		cache.clear();
	}
	
	public ReturnType invoke(Object... args) throws InvocationException {
		
		// search tuple in cache
		DispatchableMethodD<DataType> method = cache.get(args[0].getClass());

		try {
			// invoke the method
			return invokeMethod(method, args);
			
		} catch (NullPointerException e) {
			// calculate the invoked method and put it in the cache
			if (method == null) {			
				method = processClassTuple(args);
				cache.put(args[0].getClass(), method);
				
			} else {
				throw e;
			}
			
			// invoke the method
			return invokeMethod(method, args);
		}
	}
		
}
