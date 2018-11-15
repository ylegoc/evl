package org.bitbucket.evl;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import org.bitbucket.evl.exception.AmbiguousMethodException;
import org.bitbucket.evl.exception.BadNonVirtualParameterTypesException;
import org.bitbucket.evl.exception.BadNumberOfVirtualParameterTypesException;
import org.bitbucket.evl.exception.MethodComparatorInstantiationException;
import org.bitbucket.evl.exception.MethodInsertionException;
import org.bitbucket.evl.exception.NoCompatibleMethodException;
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
public abstract class MultiMethodD<ReturnType> {

	private MethodHandles.Lookup lookup = MethodHandles.lookup();
	private int dimension;
	protected MethodComparatorD methodComparator;
	private ArrayList<DispatchableMethodD> dispatchableMethods = new ArrayList<DispatchableMethodD>();
	private Class<?>[] nonVirtualParameterTypes;
	
	MultiMethodD(int dimension, MethodComparatorD methodComparator) {
		this.dimension = dimension;
		this.methodComparator = methodComparator;
	}

	public int getDimension() {
		return dimension;
	}
	
	protected abstract void resetCache();
	
	// throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException
	protected DispatchableMethodD addMethod(Method method, Object object, Comparable<?> data) throws ReflectiveOperationException {
		
		// Find the method handle.
		MethodHandle methodHandle = null;
		try {
			methodHandle = lookup.findVirtual(method.getDeclaringClass(), method.getName(), MethodType.methodType(method.getReturnType(), method.getParameterTypes())).bindTo(object);
		}
		catch (ReflectiveOperationException e) {
			// If static the method is not found.
		}
		
		// Test static method.
		if (methodHandle == null) {
			try {
				methodHandle = lookup.findStatic(method.getDeclaringClass(), method.getName(), MethodType.methodType(method.getReturnType(), method.getParameterTypes()));
			}
			catch (ReflectiveOperationException e) {
				throw e;
			}
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
		DispatchableMethodD dispatchableMethod = new DispatchableMethodD(tuple, methodHandle, object);
		dispatchableMethod.setData(data);
		
		// Add the method.
		dispatchableMethods.add(dispatchableMethod);
		
		return dispatchableMethod;
	}
	
	// not possible to set data to all the methods
	// only for non-data methods
	// throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException
	protected void addMethodFamily(Class<?> classInstance, String methodName, Object object) throws ReflectiveOperationException {
		
		// Set all the other methods lastAdded to false.
		for (DispatchableMethodD m : dispatchableMethods) {
			m.setLastAdded(false);
		}
		
		// Add all the methods.
		Method[] methods = classInstance.getMethods();
		for (Method m : methods) {
			if (m.getName().equals(methodName)) {
				DispatchableMethodD dispatchableMethod = addMethod(m, object, null);
				dispatchableMethod.setLastAdded(true);
			}
		}
	}
	
	public MultiMethodD<ReturnType> add(Class<?> classInstance, String name, Class<?>... parameterTypes) {
		
		// Set all the other methods lastAdded to false.
		for (DispatchableMethodD m : dispatchableMethods) {
			m.setLastAdded(false);
		}
		
		// Add the method.
		try {
			DispatchableMethodD dispatchableMethod = addMethod(classInstance.getMethod(name, parameterTypes), null, null);
			dispatchableMethod.setLastAdded(true);
			
		} catch (ReflectiveOperationException e) {
			throw new MethodInsertionException();
		}
		
		return this;
	}
	
	public MultiMethodD<ReturnType> add(Object object, String name, Class<?>... parameterTypes) {
		
		// Set all the other methods lastAdded to false.
		for (DispatchableMethodD m : dispatchableMethods) {
			m.setLastAdded(false);
		}
		
		// Add the method.
		try {
			DispatchableMethodD dispatchableMethod = addMethod(object.getClass().getMethod(name, parameterTypes), object, null);
			dispatchableMethod.setLastAdded(true);
		}
		catch (ReflectiveOperationException e) {
			throw new MethodInsertionException();
		}
		
		return this;
	}
	
	public MultiMethodD<ReturnType> data(Comparable<?> data) {
		
		// Find the last added methods and set data.
		for (DispatchableMethodD m : dispatchableMethods) {
			if (m.isLastAdded()) {
				m.setData(data);
			}
		}
		
		return this;
	}
	
	public MultiMethodD<ReturnType> addAll(Class<?> classInstance, String methodName) {
		
		try {
			addMethodFamily(classInstance, methodName, null);
		}
		catch (ReflectiveOperationException e) {
			throw new MethodInsertionException();
		}
		
		return this;
	}
	
	public MultiMethodD<ReturnType> addAll(Object object, String methodName) {

		try {
			addMethodFamily(object.getClass(), methodName, object);
		}
		catch (ReflectiveOperationException e) {
			throw new MethodInsertionException();
		}
		
		return this;
	}
	
	@SuppressWarnings("unchecked")
	protected DispatchableMethodD processClassTuple(Object[] args) throws MethodComparatorInstantiationException, NoCompatibleMethodException, AmbiguousMethodException {
		
		// create ClassTuple from arguments
		Class<?>[] virtualParameterTypes = new Class<?>[getDimension()];
		for (int i = 0; i < getDimension(); ++i) {
			virtualParameterTypes[i] = args[i].getClass();
		}
		MethodClassTuple methodTuple = new MethodClassTuple(virtualParameterTypes);
		
		// the method comparator is copied to avoid concurrent calls if the comparator memorizes states
		MethodComparatorD methodComparatorCopy;
		
		try {
			methodComparatorCopy = this.methodComparator.getClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new MethodComparatorInstantiationException();
		}
		
		// set the args to the comparator
		methodComparatorCopy.setArgs(args);
		
		return processClassTuple(methodComparatorCopy, methodTuple, SuperClass.calculate(methodTuple));
	}
	
	private DispatchableMethodD processClassTuple(MethodComparatorD methodComparator, MethodClassTuple tuple, HashMap<Class<?>, Integer>[] superClassSet) throws NoCompatibleMethodException, AmbiguousMethodException {
		
		// search compatible methods
		ArrayList<MethodItemD> compatibleMethodItems = new ArrayList<MethodItemD>();
		
		// iterate the list
		for (DispatchableMethodD method : dispatchableMethods) {
			MethodItemD item = CompatibleMethod.calculate(superClassSet, method);
			
			if (item != null) {
				
				// test if the function is applicable
				// the function is added if it is equals to itself
				if (methodComparator.compare(item, item) == 0) {
					compatibleMethodItems.add(item);
				}
			}
		}
		
		// search for the minimum method item
		ArrayList<MethodItemD> minMethodItems = new ArrayList<MethodItemD>();
		
		if (compatibleMethodItems.isEmpty()) {
			throw new NoCompatibleMethodException(tuple);
		}

		// there is at least 1 item
		Iterator<MethodItemD> i = compatibleMethodItems.iterator();
		minMethodItems.add(i.next());
		
		while (i.hasNext()) {
			
			MethodItemD item = i.next();
			
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
			MethodItemD minItem = minMethodItems.get(0);
			
			i = compatibleMethodItems.iterator();
			minMethodItems.add(i.next());
			
			while (i.hasNext()) {
				MethodItemD item = i.next();
				if (item != minItem && methodComparator.compare(minItem, item) != -1) {
					// minItem is not the real minimum
					throw new NoCompatibleMethodException(tuple);
				}
			}
			
			return minItem;
		}
		
		String possibleMethods = "";
		for (MethodItemD item : minMethodItems) {
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
