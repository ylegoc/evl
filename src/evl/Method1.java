package evl;

import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap;


public class Method1<ReturnType, DataType> extends MultiMethod<ReturnType, DataType> {
	
	private AbstractMap<Class<?>, DispatchableMethod<DataType>> cache;
	
	public Method1(MethodComparator<DataType> methodComparator, AbstractMap<Class<?>, DispatchableMethod<DataType>> cacheMap) {
		super(1, methodComparator);
		this.cache = cacheMap;
	}
	
	protected void resetCache() {
		cache.clear();
	}
	
	@SuppressWarnings("unchecked")
	public ReturnType invoke(Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		
		// search tuple in cache
		DispatchableMethod<DataType> method = cache.get(args[0].getClass());

		try {
			// invoke the method
			return (ReturnType)method.getMethod().invoke(method.getObject(), args);
			
		} catch (NullPointerException e) {
			// calculate the invoked method and put it in the cache
			if (method == null) {			
				method = processClassTuple(args);
				cache.put(args[0].getClass(), method);
				
			} else {
				throw e;
			}
			
			// invoke the method
			return (ReturnType)method.getMethod().invoke(method.getObject(), args);
		}
	}
		
}
