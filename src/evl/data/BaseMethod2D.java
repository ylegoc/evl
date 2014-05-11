package evl.data;

import java.util.Arrays;
import java.util.Map;

import evl.exceptions.EVLException;


public abstract class BaseMethod2D<ReturnType, DataType> extends MultiMethodD<ReturnType, DataType> {
	
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
	
	private Map<ClassTuple, DispatchableMethodD<DataType>> cache;
	
	public BaseMethod2D(MethodComparatorD<DataType> methodComparator, Map<ClassTuple, DispatchableMethodD<DataType>> cacheMap) {
		super(2, methodComparator);
		this.cache = cacheMap;
	}
	
	protected void resetCache() {
		cache.clear();
	}
	
	public ReturnType invoke(Object... args) throws EVLException {
		
		// define tuple
		ClassTuple classTuple = new ClassTuple(args[0].getClass(), args[1].getClass());
		
		// search tuple in cache
		DispatchableMethodD<DataType> method = cache.get(classTuple);

		try {
			// invoke the method
			return invokeMethod(method, args);
			
		} catch (NullPointerException e) {
			// calculate the invoked method and put it in the cache
			if (method == null) {			
				method = processClassTuple(args);
				cache.put(classTuple, method);
				
			} else {
				throw e;
			}
			
			// invoke the method
			return invokeMethod(method, args);
		}
	}
		
}
