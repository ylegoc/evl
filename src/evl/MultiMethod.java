package evl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;

import evl.exceptions.BadNonVirtualParameterTypes;
import evl.exceptions.BadNumberOfVirtualParameterTypes;

public class MultiMethod<ReturnType, DataType> {

	private int dimension;
	private MethodComparator<DataType> methodComparator;
	private AbstractMap<ClassTuple, DispatchableMethod<ReturnType, DataType>> cache;
	private ArrayList<DispatchableMethod<ReturnType, DataType>> dispatchableMethods = new ArrayList<DispatchableMethod<ReturnType, DataType>>();
	private Class<?>[] nonVirtualParameterTypes;
	
	MultiMethod(int dimension, MethodComparator<DataType> methodComparator, AbstractMap<ClassTuple, DispatchableMethod<ReturnType, DataType>> cacheMap) {
		this.dimension = dimension;
		this.methodComparator = methodComparator;
		this.cache = cacheMap;
	}
	
	public void add(Method method, Object object, DataType data) throws BadNumberOfVirtualParameterTypes, BadNonVirtualParameterTypes {
		
		Class<?>[] newParameterTypes = method.getParameterTypes();
		
		// check parameter types
		if (newParameterTypes.length < dimension) {
			throw new BadNumberOfVirtualParameterTypes();
		} 
	
		Class<?>[] newVirtualParameterTypes = new Class<?>[dimension];
		
		for (int i = 0; i < dimension; ++i) {
			newVirtualParameterTypes[i] = newParameterTypes[i];
		}
		
		Class<?>[] newNonVirtualParameterTypes = new Class<?>[newParameterTypes.length - dimension];
		for (int i = dimension; i < newParameterTypes.length; ++i) {
			newNonVirtualParameterTypes[i] = newParameterTypes[i];
		}
		
		if (nonVirtualParameterTypes == null) {
			// first inserted method
			nonVirtualParameterTypes = newNonVirtualParameterTypes;
			
		} else {
			// check the equality with non virtual parameter types
			if (!Arrays.equals(nonVirtualParameterTypes, newNonVirtualParameterTypes)) {
				throw new BadNonVirtualParameterTypes();
			}
		}
		
		ClassTuple tuple = new ClassTuple(newVirtualParameterTypes);
		DispatchableMethod<ReturnType, DataType> dispatchableMethod = new DispatchableMethod<ReturnType, DataType>(tuple, method, object);
		dispatchableMethod.setData(data);
		
		dispatchableMethods.add(dispatchableMethod);
	}
	
	public void add(Method method, Object object) throws BadNumberOfVirtualParameterTypes, BadNonVirtualParameterTypes {
		this.add(method, object, null);
	}
	
	public ReturnType invoke(Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// create ClassTuple from arguments
		Class<?>[] virtualParameterTypes = new Class<?>[dimension];
		for (int i = 0; i < dimension; ++i) {
			virtualParameterTypes[i] = args[i].getClass();
		}
		ClassTuple tuple = new ClassTuple(virtualParameterTypes);
		
		// search tuple in cache
		DispatchableMethod<ReturnType, DataType> method = cache.get(tuple);
		
		// calculate the invoked method and put it in the cache
		if (method == null) {
			method = find_min_method(tuple);
			cache.put(tuple, method);
		}
		
		// invoke the method
		return method.invoke(args);
		
	}

	private DispatchableMethod<ReturnType, DataType> find_min_method(ClassTuple tuple) {
		return null;
	}
	
}
