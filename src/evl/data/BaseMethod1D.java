package evl.data;

import java.util.AbstractMap;

import evl.exceptions.EVLException;


public abstract class BaseMethod1D<ReturnType, DataType> extends MultiMethodD<ReturnType, DataType> {
	
	private AbstractMap<Class<?>, DispatchableMethodD<DataType>> cache;
	
	public BaseMethod1D(MethodComparatorD<DataType> methodComparator, AbstractMap<Class<?>, DispatchableMethodD<DataType>> cacheMap) {
		super(1, methodComparator);
		this.cache = cacheMap;
	}
	
	protected void resetCache() {
		cache.clear();
	}
	
	public ReturnType invoke(Object... args) throws EVLException {
		
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
