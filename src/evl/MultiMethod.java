package evl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import evl.exceptions.AmbiguousMethodException;
import evl.exceptions.BadNonVirtualParameterTypesException;
import evl.exceptions.BadNumberOfVirtualParameterTypesException;
import evl.exceptions.EVLException;
import evl.exceptions.InvocationException;
import evl.exceptions.MethodComparatorInstantiationException;
import evl.exceptions.NoCompatibleMethodException;
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
	
	public void add(Method method, Object object, DataType data) throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException {
		
		Class<?>[] newParameterTypes = method.getParameterTypes();
		
		// check parameter types
		if (newParameterTypes.length < dimension) {
			throw new BadNumberOfVirtualParameterTypesException();
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
				throw new BadNonVirtualParameterTypesException();
			}
		}
		
		// the cache must be cleared
		resetCache();
		
		try {
			method.setAccessible(true);
		} catch (SecurityException e) {
			// do nothing
		}
		MethodClassTuple tuple = new MethodClassTuple(newVirtualParameterTypes);
		DispatchableMethod<DataType> dispatchableMethod = new DispatchableMethod<DataType>(tuple, method, object);
		dispatchableMethod.setData(data);
		
		dispatchableMethods.add(dispatchableMethod);
	}
	
	public void add(Method method, Object object) throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException {
		this.add(method, object, null);
	}
	
	public abstract ReturnType invoke(Object... args) throws EVLException;
	
	@SuppressWarnings("unchecked")
	protected ReturnType invokeMethod(DispatchableMethod<DataType> method, Object[] args) throws InvocationException {
		// invoke the method
		try {
			return (ReturnType)method.getMethod().invoke(method.getObject(), args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new InvocationException();
		}
	}
	
	@SuppressWarnings("unchecked")
	protected DispatchableMethod<DataType> processClassTuple(Object[] args) throws MethodComparatorInstantiationException, NoCompatibleMethodException, AmbiguousMethodException {
		
		// create ClassTuple from arguments
		Class<?>[] virtualParameterTypes = new Class<?>[getDimension()];
		for (int i = 0; i < getDimension(); ++i) {
			virtualParameterTypes[i] = args[i].getClass();
		}
		MethodClassTuple methodTuple = new MethodClassTuple(virtualParameterTypes);
		
		// the method comparator is copied to avoid concurrent calls if the comparator memorizes states
		MethodComparator<DataType> methodComparatorCopy;
		
		try {
			methodComparatorCopy = this.methodComparator.getClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new MethodComparatorInstantiationException();
		}
		
		return processClassTuple(methodComparatorCopy, methodTuple, SuperClass.calculate(methodTuple));
	}
	
	private DispatchableMethod<DataType> processClassTuple(MethodComparator<DataType> methodComparator, MethodClassTuple tuple, HashMap<Class<?>, Integer>[] superClassSet) throws NoCompatibleMethodException, AmbiguousMethodException {
		
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
			throw new NoCompatibleMethodException(tuple);
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
		
		String possibleMethods = "";
		for (MethodItem<DataType> item : minMethodItems) {
			possibleMethods += "\t" + item.getMethod() + "\n";
		}
		
		throw new AmbiguousMethodException(tuple, possibleMethods);
	}
	
}
