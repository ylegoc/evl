package evl.data;

import java.util.AbstractMap;
import java.util.Arrays;

import evl.exceptions.EVLException;


public abstract class BaseMethod4D<ReturnType, DataType> extends MultiMethodD<ReturnType, DataType> {
	
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
	
	private AbstractMap<ClassTuple, DispatchableMethodD<DataType>> cache;
	
	public BaseMethod4D(MethodComparatorD<DataType> methodComparator, AbstractMap<ClassTuple, DispatchableMethodD<DataType>> cacheMap) {
		super(4, methodComparator);
		this.cache = cacheMap;
	}
	
	protected void resetCache() {
		cache.clear();
	}
	
	public ReturnType invoke(Object... args) throws EVLException {
		
		// define tuple
		ClassTuple classTuple = new ClassTuple(args[0].getClass(), args[1].getClass(), args[2].getClass(), args[3].getClass());
		
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
