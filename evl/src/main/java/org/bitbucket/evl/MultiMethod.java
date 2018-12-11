package org.bitbucket.evl;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bitbucket.evl.exception.AmbiguousMethodException;
import org.bitbucket.evl.exception.BadNonVirtualParameterTypesException;
import org.bitbucket.evl.exception.BadNumberOfVirtualParameterTypesException;
import org.bitbucket.evl.exception.MethodComparatorInstantiationException;
import org.bitbucket.evl.exception.MethodInsertionException;
import org.bitbucket.evl.exception.NoCompatibleMethodException;
import org.bitbucket.evl.lookup.CasesLookup;
import org.bitbucket.evl.util.ClassTuple;
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
 */
public abstract class MultiMethod<ReturnType> {
	
	private static final String DEFAULT_METHOD_NAME = "match";

	private MethodHandles.Lookup lookup = MethodHandles.lookup();
	private int dimension;
	protected MatchMethodComparator methodComparator;
	private ArrayList<MatchMethod> matchMethods = new ArrayList<MatchMethod>();
	private Class<?>[] nonVirtualParameterTypes;
	
	MultiMethod(int dimension, MatchMethodComparator methodComparator) {
		this.dimension = dimension;
		this.methodComparator = methodComparator;
	}

	public int getDimension() {
		return dimension;
	}
	
	public List<MatchMethod> getMatchMethods() {
		return matchMethods;
	}
	
	protected abstract void resetCache();
	
	// throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException
	protected MatchMethod addMethod(MethodHandles.Lookup lookup, Method method, Object object, Comparable<?> data) throws ReflectiveOperationException {
		
		// Find the method handle.
		MethodHandle methodHandle = null;
		try {
			methodHandle = lookup.findVirtual(method.getDeclaringClass(), method.getName(), MethodType.methodType(method.getReturnType(), method.getParameterTypes())).bindTo(object);
		}
		catch (ReflectiveOperationException e) {
			// If static, the method is not found.
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
		ClassTuple tuple = new ClassTuple(newVirtualParameterTypes);
		MatchMethod MatchMethod = new MatchMethod(tuple, methodHandle, object);
		MatchMethod.setData(data);
		
		// Add the method.
		matchMethods.add(MatchMethod);
		
		return MatchMethod;
	}
	
	// not possible to set data to all the methods
	// only for non-data methods
	// throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException
	protected void addMethodFamily(MethodHandles.Lookup lookup, Class<?> classInstance, String methodName, Object object) throws ReflectiveOperationException {
		
		// Set all the other methods lastAdded to false.
		for (MatchMethod m : matchMethods) {
			m.setLastAdded(false);
		}
		
		// Add all the methods.
		Method[] methods = classInstance.getDeclaredMethods();
		for (Method m : methods) {
			if (m.getName().equals(methodName)) {
				MatchMethod MatchMethod = addMethod(lookup, m, object, null);
				MatchMethod.setLastAdded(true);
			}
		}
	}
	
	public MultiMethod<ReturnType> add(Class<?> classInstance, String name, Class<?>... parameterTypes) {
		
		// Set all the other methods lastAdded to false.
		for (MatchMethod m : matchMethods) {
			m.setLastAdded(false);
		}
		
		// Add the method.
		try {
			MatchMethod MatchMethod = addMethod(lookup, classInstance.getMethod(name, parameterTypes), null, null);
			MatchMethod.setLastAdded(true);
			
		} catch (ReflectiveOperationException e) {
			throw new MethodInsertionException(e.getMessage());
		}
		
		return this;
	}
	
	public MultiMethod<ReturnType> add(Object object, String name, Class<?>... parameterTypes) {
		
		// Set all the other methods lastAdded to false.
		for (MatchMethod m : matchMethods) {
			m.setLastAdded(false);
		}
		
		// Add the method.
		try {
			MatchMethod MatchMethod = addMethod(lookup, object.getClass().getMethod(name, parameterTypes), object, null);
			MatchMethod.setLastAdded(true);
		}
		catch (ReflectiveOperationException e) {
			throw new MethodInsertionException(e.getMessage());
		}
		
		return this;
	}
	
	public MultiMethod<ReturnType> data(Comparable<?> data) {
		
		// Find the last added methods and set data.
		for (MatchMethod m : matchMethods) {
			if (m.isLastAdded()) {
				m.setData(data);
			}
		}
		
		return this;
	}
	
	public MultiMethod<ReturnType> add(Class<?> classInstance, String methodName) {
		
		try {
			addMethodFamily(lookup, classInstance, methodName, null);
		}
		catch (ReflectiveOperationException e) {
			throw new MethodInsertionException(e.getMessage());
		}
		
		return this;
	}
	
	public MultiMethod<ReturnType> add(Object object, String methodName) {

		try {
			addMethodFamily(lookup, object.getClass(), methodName, object);
		}
		catch (ReflectiveOperationException e) {
			e.printStackTrace();
			throw new MethodInsertionException(e.getMessage());
		}
		
		return this;
	}
	
	public MultiMethod<ReturnType> add(Object object) {
		
		return this.add(object, DEFAULT_METHOD_NAME);
	}
	
	public MultiMethod<ReturnType> add(Cases cases) {
		
		// Get the current lookup.
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		try {
			// Access to the lookup of the anonymous Cases object.
			lookup = CasesLookup.privateLookupIn(cases.getClass(), lookup);
			
			try {
				addMethodFamily(lookup, cases.getClass(), "match", cases);
			}
			catch (ReflectiveOperationException e) {
				throw new MethodInsertionException(e.getMessage());
			}
			
		} catch (IllegalAccessException e) {
			throw new MethodInsertionException(e.getMessage());
		}
		
		return this;
	}
	
	protected MatchMethod processClassTuple(Object[] args) throws MethodComparatorInstantiationException, NoCompatibleMethodException, AmbiguousMethodException {
		
		// create ClassTuple from arguments
		Class<?>[] virtualParameterTypes = new Class<?>[getDimension()];
		for (int i = 0; i < getDimension(); ++i) {
			virtualParameterTypes[i] = args[i].getClass();
		}
		ClassTuple methodTuple = new ClassTuple(virtualParameterTypes);
		
		// set the args to the comparator.
		methodComparator.setArgs(args);
		
		return processClassTuple(methodTuple, SuperClass.calculate(methodTuple));
	}

	protected static MatchMethodItem calculateCompatibleMethod(HashMap<Class<?>, Integer>[] superClassSet, MatchMethod method) {
		
		Class<?>[] classTuple = method.getClassTuple().get();
		int[] distanceTuple = new int[superClassSet.length];
		
		for (int i = 0; i < superClassSet.length; ++i) {
			
			Integer distance = superClassSet[i].get(classTuple[i]);
			
			if (distance != null) {
				distanceTuple[i] = distance;
				
			} else {
				return null;
			}
		}
		
		return new MatchMethodItem(method, distanceTuple);
	}
	
	private MatchMethod processClassTuple(ClassTuple tuple, HashMap<Class<?>, Integer>[] superClassSet) throws NoCompatibleMethodException, AmbiguousMethodException {
		
		// search compatible methods
		ArrayList<MatchMethodItem> compatibleMethodItems = new ArrayList<MatchMethodItem>();
		
		// iterate the list
		for (MatchMethod method : matchMethods) {
			MatchMethodItem item = calculateCompatibleMethod(superClassSet, method);
			
			if (item != null) {
				
				// test if the function is applicable
				// the function is added if it is equals to itself
				if (methodComparator.compare(item, item) == 0) {
					compatibleMethodItems.add(item);
				}
			}
		}
		
		// search for the minimum method item
		ArrayList<MatchMethodItem> minMethodItems = new ArrayList<MatchMethodItem>();
		
		if (compatibleMethodItems.isEmpty()) {
			throw new NoCompatibleMethodException(tuple);
		}

		// there is at least 1 item
		Iterator<MatchMethodItem> i = compatibleMethodItems.iterator();
		minMethodItems.add(i.next());
		
		while (i.hasNext()) {
			
			MatchMethodItem item = i.next();
			
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
			MatchMethodItem minItem = minMethodItems.get(0);
			
			i = compatibleMethodItems.iterator();
			minMethodItems.add(i.next());
			
			while (i.hasNext()) {
				MatchMethodItem item = i.next();
				if (item != minItem && methodComparator.compare(minItem, item) != -1) {
					// minItem is not the real minimum
					throw new NoCompatibleMethodException(tuple);
				}
			}
			
			return minItem;
		}
		
		String possibleMethods = "";
		for (MatchMethodItem item : minMethodItems) {
			possibleMethods += "\t" + item.getMethod() + "\n";
		}
		
		throw new AmbiguousMethodException(tuple, possibleMethods);
	}

	@Override
	public String toString() {
		return "MultiMethod [dimension=" + dimension + ", matchMethodComparator="
				+ methodComparator.getClass().getCanonicalName() + ", matchMethods="
				+ matchMethods.size() + ", nonVirtualParameterTypes="
				+ Arrays.toString(nonVirtualParameterTypes) + "]";
	}
	
	
}
