package evl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import evl.exceptions.BadNonVirtualParameterTypes;
import evl.exceptions.BadNumberOfVirtualParameterTypes;
import evl.util.CompatibleMethod;
import evl.util.SuperClass;


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
			// test with MethodHandle
			return (ReturnType)method.getMethod().invoke(method.getObject(), args);
			
		} catch (NullPointerException e) {
			// calculate the invoked method and put it in the cache
			if (method == null) {			
				// create ClassTuple from arguments
				Class<?>[] virtualParameterTypes = new Class<?>[getDimension()];
				for (int i = 0; i < getDimension(); ++i) {
					virtualParameterTypes[i] = args[i].getClass();
				}
				MethodClassTuple methodTuple = new MethodClassTuple(virtualParameterTypes);
				method = processClassTuple(methodTuple);
				
				cache.put(args[0].getClass(), method);
			} else {
				throw e;
			}
			
			// invoke the method
			return (ReturnType)method.getMethod().invoke(method.getObject(), args);
		}
	}
		
}
