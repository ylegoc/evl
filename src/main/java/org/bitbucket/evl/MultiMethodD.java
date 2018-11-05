package org.bitbucket.evl;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import org.bitbucket.evl.exception.AmbiguousMethodException;
import org.bitbucket.evl.exception.BadNonVirtualParameterTypesException;
import org.bitbucket.evl.exception.BadNumberOfVirtualParameterTypesException;
import org.bitbucket.evl.exception.InvocationException;
import org.bitbucket.evl.exception.MethodComparatorInstantiationException;
import org.bitbucket.evl.exception.MethodInvocationException;
import org.bitbucket.evl.exception.NoCompatibleMethodException;
import org.bitbucket.evl.util.CompatibleMethod;
import org.bitbucket.evl.util.MethodClassTuple;
import org.bitbucket.evl.util.SuperClass;

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

	private MethodHandles.Lookup lookup = MethodHandles.lookup();
	private int dimension;
	protected MethodComparatorD<DataType> methodComparator;
	private ArrayList<DispatchableMethodD<DataType>> dispatchableMethods = new ArrayList<DispatchableMethodD<DataType>>();
	private Class<?>[] nonVirtualParameterTypes;
	
	MultiMethodD(int dimension, MethodComparatorD<DataType> methodComparator) {
		this.dimension = dimension;
		this.methodComparator = methodComparator;
	}

	public int getDimension() {
		return dimension;
	}
	
	protected abstract void resetCache();
	
	// throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException
	protected void addMethod(Method method, Object object, DataType data) {
		
		// Find the method handle.
		MethodHandle methodHandle = null;
		try {
			methodHandle = lookup.findVirtual(method.getDeclaringClass(), method.getName(), MethodType.methodType(method.getReturnType(), method.getParameterTypes()))
									.bindTo(object);
			
		} catch (NoSuchMethodException | IllegalAccessException e1) {
			// Should not happen.
			System.err.println("Unable to find method handle.");
		}
		
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
		DispatchableMethodD<DataType> dispatchableMethod = new DispatchableMethodD<DataType>(tuple, methodHandle, object);
		dispatchableMethod.setData(data);
		
		dispatchableMethods.add(dispatchableMethod);
	}
	
	// not possible to set data to all the methods
	// only for non-data methods
	// throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException
	protected void addMethodFamily(Class<?> classInstance, String methodName, Object object) {
		Method[] methods = classInstance.getMethods();
		for (Method m : methods) {
			if (m.getName().equals(methodName)) {
				addMethod(m, object, null);
			}
		}
	}
	
	public abstract ReturnType invoke(Object arg1) throws Throwable;
	
//	@SuppressWarnings("unchecked")
//	protected ReturnType invokeMethod(DispatchableMethodD<DataType> method, Object[] args) throws MethodInvocationException {
//		// invoke the method
//		try {
//			return (ReturnType)method.getMethod().invoke(method.getObject(), args);
//		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//			throw new MethodInvocationException();
//		}
//	}
	
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
		
		// set the args to the comparator
		methodComparatorCopy.setArgs(args);
		
		return processClassTuple(methodComparatorCopy, methodTuple, SuperClass.calculate(methodTuple));
	}
	
	private DispatchableMethodD<DataType> processClassTuple(MethodComparatorD<DataType> methodComparator, MethodClassTuple tuple, HashMap<Class<?>, Integer>[] superClassSet) throws NoCompatibleMethodException, AmbiguousMethodException {
		
		// search compatible methods
		ArrayList<MethodItemD<DataType>> compatibleMethodItems = new ArrayList<MethodItemD<DataType>>();
		
		// iterate the list
		for (DispatchableMethodD<DataType> method : dispatchableMethods) {
			MethodItemD<DataType> item = CompatibleMethod.calculate(superClassSet, method);
			
			if (item != null) {
				
				// test if the function is applicable
				// the function is added if it is equals to itself
				if (methodComparator.compare(item, item) == 0) {
					compatibleMethodItems.add(item);
				}
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
			
			// check that min is really min
			MethodItemD<DataType> minItem = minMethodItems.get(0);
			
			i = compatibleMethodItems.iterator();
			minMethodItems.add(i.next());
			
			while (i.hasNext()) {
				MethodItemD<DataType> item = i.next();
				if (item != minItem && methodComparator.compare(minItem, item) != -1) {
					// minItem is not the real minimum
					throw new NoCompatibleMethodException(tuple);
				}
			}
			
			return minItem;
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
