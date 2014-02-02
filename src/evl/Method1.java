package evl;

import java.util.AbstractMap;

import evl.exceptions.EVLException;


public class Method1<ReturnType, DataType> extends MultiMethod<ReturnType, DataType> {
	
	private AbstractMap<Class<?>, DispatchableMethod<DataType>> cache;
	
	public Method1(MethodComparator<DataType> methodComparator, AbstractMap<Class<?>, DispatchableMethod<DataType>> cacheMap) {
		super(1, methodComparator);
		this.cache = cacheMap;
	}
	
	protected void resetCache() {
		cache.clear();
	}
	
	public ReturnType invoke(Object... args) throws EVLException {
		
		// search tuple in cache
		DispatchableMethod<DataType> method = cache.get(args[0].getClass());

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
