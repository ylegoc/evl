package evl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import evl.exceptions.BadNonVirtualParameterTypes;
import evl.exceptions.BadNumberOfVirtualParameterTypes;
import evl.util.CompatibleMethod;
import evl.util.SuperClass;

/*
 * Performance tests show that:
 * - MultiMethod call is 9 times slower than method call
 * - Time is spent approximatively:
 *  - 25% method call
 *  - 25% cache search for HashMap
 *  - 50% reflection method invocation
 * - Use static methods with null objects has no incidence 
 * - No need for invokeCache since performance is not good enough
 * 
 * It is not possible to optimize with MethodHandle objects.
 * Only the MethodHandle.invokeExact is fast, but it requires to define a bridge 
 * that would be only possible with code template (C++) to generate it.
 * At least the virtual arguments could be avoided to be checked.
 */
public abstract class MultiMethod<ReturnType, DataType> {

	private int dimension;
	private MethodComparator<DataType> methodComparator;
	private ArrayList<DispatchableMethod<DataType>> dispatchableMethods = new ArrayList<DispatchableMethod<DataType>>();
	private Class<?>[] nonVirtualParameterTypes;
	
	public MultiMethod(int dimension, MethodComparator<DataType> methodComparator) {
		this.dimension = dimension;
		this.methodComparator = methodComparator;
	}

	public int getDimension() {
		return dimension;
	}
	
	protected abstract void resetCache();
	
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
			newNonVirtualParameterTypes[i - dimension] = newParameterTypes[i];
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
		
		// the cache must be cleared
		resetCache();
		
		method.setAccessible(true);
		MethodClassTuple tuple = new MethodClassTuple(newVirtualParameterTypes);
		DispatchableMethod<DataType> dispatchableMethod = new DispatchableMethod<DataType>(tuple, method, object);
		dispatchableMethod.setData(data);
		
		dispatchableMethods.add(dispatchableMethod);
	}
	
	public void add(Method method, Object object) throws BadNumberOfVirtualParameterTypes, BadNonVirtualParameterTypes {
		this.add(method, object, null);
	}
	
	public abstract ReturnType invoke(Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException;
	
/*	
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
*/	
	@SuppressWarnings("unchecked")
	protected DispatchableMethod<DataType> processClassTuple(Object[] args) throws InstantiationException, IllegalAccessException {
		
		// create ClassTuple from arguments
		Class<?>[] virtualParameterTypes = new Class<?>[getDimension()];
		for (int i = 0; i < getDimension(); ++i) {
			virtualParameterTypes[i] = args[i].getClass();
		}
		MethodClassTuple methodTuple = new MethodClassTuple(virtualParameterTypes);
		
		// the method comparator is copied to avoid concurrent calls if the comparator memorizes states
		return processClassTuple(this.methodComparator.getClass().newInstance(), methodTuple, SuperClass.calculate(methodTuple));
	}
	
	private DispatchableMethod<DataType> processClassTuple(MethodComparator<DataType> methodComparator, MethodClassTuple tuple, HashMap<Class<?>, Integer>[] superClassSet) {
		
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
		ArrayList<MethodItem<DataType>> minMethodItems = new ArrayList<MethodItem<DataType>>();
		
		if (compatibleMethodItems.isEmpty()) {
			// TODO exception
			System.err.println("no compatible method");
			return null;
		}

		// there is at least 1 item
		Iterator<MethodItem<DataType>> i = compatibleMethodItems.iterator();
		minMethodItems.add(i.next());
		
		while (i.hasNext()) {
			
			MethodItem<DataType> item = i.next();
			
			int result = methodComparator.compare(item, minMethodItems.get(0));
			
			if (result == 0) {
				minMethodItems.add(item);
			} else if (result < 0) {
				minMethodItems.clear();
				minMethodItems.add(item);
			}
		}
		
		// test the result, the container cannot be empty
		if (minMethodItems.size() == 1) {
			return minMethodItems.get(0);
		}
		
		// TODO ambiguity exception
		System.err.println("ambiguous method");
		return null;
	}
	
}
