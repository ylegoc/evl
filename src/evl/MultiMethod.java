package evl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import evl.exceptions.BadNonVirtualParameterTypes;
import evl.exceptions.BadNumberOfVirtualParameterTypes;
import evl.util.CompatibleMethod;
import evl.util.SuperClass;

public class MultiMethod<ReturnType, DataType> {

	private int dimension;
	private MethodComparator<DataType> methodComparator;
	private AbstractMap<ClassTuple, DispatchableMethod<DataType>> cache;
	private ArrayList<DispatchableMethod<DataType>> dispatchableMethods = new ArrayList<DispatchableMethod<DataType>>();
	private Class<?>[] nonVirtualParameterTypes;
	
	MultiMethod(int dimension, MethodComparator<DataType> methodComparator, AbstractMap<ClassTuple, DispatchableMethod<DataType>> cacheMap) {
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
		DispatchableMethod<DataType> dispatchableMethod = new DispatchableMethod<DataType>(tuple, method, object);
		dispatchableMethod.setData(data);
		
		dispatchableMethods.add(dispatchableMethod);
	}
	
	public void add(Method method, Object object) throws BadNumberOfVirtualParameterTypes, BadNonVirtualParameterTypes {
		this.add(method, object, null);
	}
	
	@SuppressWarnings("unchecked")
	public ReturnType invoke(Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		// create ClassTuple from arguments
		Class<?>[] virtualParameterTypes = new Class<?>[dimension];
		for (int i = 0; i < dimension; ++i) {
			virtualParameterTypes[i] = args[i].getClass();
		}
		ClassTuple tuple = new ClassTuple(virtualParameterTypes);
		
		// search tuple in cache
		DispatchableMethod<DataType> method = cache.get(tuple);
		
		// calculate the invoked method and put it in the cache
		if (method == null) {
			method = processClassTuple(tuple);
			cache.put(tuple, method);
		}
		
		// invoke the method
		return (ReturnType)method.getMethod().invoke(method.getObject(), args);
		
	}

	@SuppressWarnings("unchecked")
	private DispatchableMethod<DataType> processClassTuple(ClassTuple tuple) throws InstantiationException, IllegalAccessException {
		
		// the method comparator is copied to avoid concurrent calls if the comparator memorizes states
		return processClassTuple(this.methodComparator.getClass().newInstance(), tuple, SuperClass.calculate(tuple));
	}
	
	private DispatchableMethod<DataType> processClassTuple(MethodComparator<DataType> methodComparator, ClassTuple tuple, HashMap<Class<?>, Integer>[] superClassSet) {
		
		// search compatible methods
		ArrayList<MethodItem<DataType>> compatibleMethodItems = new ArrayList<MethodItem<DataType>>();
		
		// iterate the list
		for (DispatchableMethod<DataType> method : dispatchableMethods) {
			MethodItem<DataType> item = CompatibleMethod.calculate(superClassSet, method);
			
			if (item != null) {
				compatibleMethodItems.add(item);
			}
		}
		
		// search for the minimum method item
		// ...
		
		return null;
	}
	
}
