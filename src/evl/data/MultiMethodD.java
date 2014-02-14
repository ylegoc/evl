package evl.data;

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
import evl.util.MethodClassTuple;
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
public abstract class MultiMethodD<ReturnType, DataType> {

	private int dimension;
	private MethodComparatorD<DataType> methodComparator;
	private ArrayList<DispatchableMethodD<DataType>> dispatchableMethods = new ArrayList<DispatchableMethodD<DataType>>();
	private Class<?>[] nonVirtualParameterTypes;
	
	public MultiMethodD(int dimension, MethodComparatorD<DataType> methodComparator) {
		this.dimension = dimension;
		this.methodComparator = methodComparator;
	}

	public int getDimension() {
		return dimension;
	}
	
	protected abstract void resetCache();
	
	protected void addMethod(Method method, Object object, DataType data) throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException {
		
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
		DispatchableMethodD<DataType> dispatchableMethod = new DispatchableMethodD<DataType>(tuple, method, object);
		dispatchableMethod.setData(data);
		
		dispatchableMethods.add(dispatchableMethod);
	}
	
	// not possible to set data to all the methods
	// only for non-data methods
	protected void addMethodFamily(Class<?> classInstance, String methodName, Object object) throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException {
		Method[] methods = classInstance.getMethods();
		for (Method m : methods) {
			if (m.getName().equals(methodName)) {
				addMethod(m, object, null);
			}
		}
	}
	
	public abstract ReturnType invoke(Object... args) throws EVLException;
	
	@SuppressWarnings("unchecked")
	protected ReturnType invokeMethod(DispatchableMethodD<DataType> method, Object[] args) throws InvocationException {
		// invoke the method
		try {
			return (ReturnType)method.getMethod().invoke(method.getObject(), args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new InvocationException();
		}
	}
	
	@SuppressWarnings("unchecked")
	protected DispatchableMethodD<DataType> processClassTuple(Object[] args) throws MethodComparatorInstantiationException, NoCompatibleMethodException, AmbiguousMethodException {
		
		// create ClassTuple from arguments
		Class<?>[] virtualParameterTypes = new Class<?>[getDimension()];
		for (int i = 0; i < getDimension(); ++i) {
			virtualParameterTypes[i] = args[i].getClass();
		}
		MethodClassTuple methodTuple = new MethodClassTuple(virtualParameterTypes);
		
		// the method comparator is copied to avoid concurrent calls if the comparator memorizes states
		MethodComparatorD<DataType> methodComparatorCopy;
		
		try {
			methodComparatorCopy = this.methodComparator.getClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new MethodComparatorInstantiationException();
		}
		
		return processClassTuple(methodComparatorCopy, methodTuple, SuperClass.calculate(methodTuple));
	}
	
	private DispatchableMethodD<DataType> processClassTuple(MethodComparatorD<DataType> methodComparator, MethodClassTuple tuple, HashMap<Class<?>, Integer>[] superClassSet) throws NoCompatibleMethodException, AmbiguousMethodException {
		
		// search compatible methods
		ArrayList<MethodItemD<DataType>> compatibleMethodItems = new ArrayList<MethodItemD<DataType>>();
		
		// iterate the list
		for (DispatchableMethodD<DataType> method : dispatchableMethods) {
			MethodItemD<DataType> item = CompatibleMethod.calculate(superClassSet, method);
			
			if (item != null) {
				compatibleMethodItems.add(item);
			}
		}
		
		// search for the minimum method item
		ArrayList<MethodItemD<DataType>> minMethodItems = new ArrayList<MethodItemD<DataType>>();
		
		if (compatibleMethodItems.isEmpty()) {
			throw new NoCompatibleMethodException(tuple);
		}

		// there is at least 1 item
		Iterator<MethodItemD<DataType>> i = compatibleMethodItems.iterator();
		minMethodItems.add(i.next());
		
		while (i.hasNext()) {
			
			MethodItemD<DataType> item = i.next();
			
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
		for (MethodItemD<DataType> item : minMethodItems) {
			possibleMethods += "\t" + item.getMethod() + "\n";
		}
		
		throw new AmbiguousMethodException(tuple, possibleMethods);
	}

	@Override
	public String toString() {
		return "MultiMethodD [dimension=" + dimension + ", methodComparator="
				+ methodComparator + ", dispatchableMethods="
				+ dispatchableMethods + ", nonVirtualParameterTypes="
				+ Arrays.toString(nonVirtualParameterTypes) + "]";
	}
	
	
}
