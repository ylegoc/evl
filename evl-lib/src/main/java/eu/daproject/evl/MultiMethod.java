/*******************************************************************************
 * Copyright 2019 The EVL authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package eu.daproject.evl;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import eu.daproject.evl.exception.AmbiguousMethodException;
import eu.daproject.evl.exception.BadNonVirtualParameterTypesException;
import eu.daproject.evl.exception.BadNumberOfVirtualParameterTypesException;
import eu.daproject.evl.exception.MethodInsertionException;
import eu.daproject.evl.exception.NoMatchingMethodException;
import eu.daproject.evl.lookup.CasesLookup;
import eu.daproject.evl.util.ClassTuple;
import eu.daproject.evl.util.SuperClass;

/**
 * Main class of the EVL library providing an abstract multimethod with a dimension which represents the number of "virtual" parameters.
 *
 * @param <ReturnType> the return type
 */
public abstract class MultiMethod<ReturnType> {
	
	private static final String DEFAULT_METHOD_NAME = "match";

	private MethodHandles.Lookup lookup = MethodHandles.lookup();
	private int dimension;
	protected MethodComparator methodComparator;
	private HashMap<ClassTuple, InvokableMethod> methods = new HashMap<ClassTuple, InvokableMethod>();
	private Class<?>[] nonVirtualParameterTypes;
	
	/**
	 * Constructs a multimethod with the dimension and a method comparator.
	 * @param dimension the number of "virtual" parameters
	 * @param methodComparator the method comparator
	 */
	MultiMethod(int dimension, MethodComparator methodComparator) {
		this.dimension = dimension;
		this.methodComparator = methodComparator;
	}

	/**
	 * Returns the dimension of the multimethod.
	 * @return the dimension
	 */
	public int getDimension() {
		return dimension;
	}
	
	/**
	 * Returns the collection of added methods.
	 * @return the collection of methods
	 */
	public Collection<InvokableMethod> getMethods() {
		return methods.values();
	}
	
	/**
	 * Sets the associated data e.g. a {@link Priority} object of the method.
	 * Resets the cache.
	 * @param data the associated data
	 * @param tuple the tuple of types
	 */
	public void setData(Comparable<?> data, Class<?>... tuple) {
		clearCache();
		methods.get(new ClassTuple(tuple)).setData(data);
	}
	
	/**
	 * Clears the cache.
	 */
	public abstract void clearCache();
	
	/**
	 * Sets the method comparator.
	 * @param methodComparator the method comparator
	 * @return this instance
	 */
	public MultiMethod<ReturnType> comparator(MethodComparator methodComparator) {
		this.methodComparator = methodComparator;
		return this;
	}
	
	/**
	 * Adds a method with the caller and the associated lookup and some optional data.
	 * @param lookup the lookup
	 * @param method the method
	 * @param object the caller
	 * @param data the data
	 * @return the inserted method
	 * @throws MethodInsertionException
	 * @throws BadNumberOfVirtualParameterTypesException
	 * @throws BadNonVirtualParameterTypesException
	 */
	protected InvokableMethod addMethod(MethodHandles.Lookup lookup, java.lang.reflect.Method method, Object object, Comparable<?> data) {
		
		// Find the method handle.
		MethodHandle methodHandle = null;
		try {
			methodHandle = lookup.findVirtual(method.getDeclaringClass(), method.getName(), MethodType.methodType(method.getReturnType(), method.getParameterTypes())).bindTo(object);
		}
		catch (ReflectiveOperationException e) {
			// If static, the method is not found.
		}
		
		// If the method handle has not been found, test if the method is static.
		if (methodHandle == null) {
			try {
				methodHandle = lookup.findStatic(method.getDeclaringClass(), method.getName(), MethodType.methodType(method.getReturnType(), method.getParameterTypes()));
			}
			catch (ReflectiveOperationException e) {
				// The method is not found, so we throw the exception.
				throw new MethodInsertionException(e.getMessage());
			}
		}
		
		// Check the parameter types.
		Class<?>[] newParameterTypes = method.getParameterTypes();
		
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
		
		// Update the non virtual parameter types if this is the first inserted method.
		if (nonVirtualParameterTypes == null) {
			nonVirtualParameterTypes = newNonVirtualParameterTypes;
		}
		else {
			// Check the equality with the non virtual parameter types of the first inserted method.
			if (!Arrays.equals(nonVirtualParameterTypes, newNonVirtualParameterTypes)) {
				throw new BadNonVirtualParameterTypesException();
			}
		}
		
		// The cache must be cleared.
		clearCache();

		// Create the class tuple and add the new method.
		ClassTuple tuple = new ClassTuple(newVirtualParameterTypes);
		InvokableMethod newMethod = new InvokableMethod(tuple, methodHandle, object);
		newMethod.setData(data);
		methods.put(tuple, newMethod);
		
		return newMethod;
	}
	
	/**
	 * Adds a family of methods with the caller.
	 * @param lookup the lookup
	 * @param classInstance the class instance
	 * @param methodName the method name, all the overloaded methods with this name are added
	 * @param object the caller
	 * @throws MethodInsertionException
	 * @throws BadNumberOfVirtualParameterTypesException
	 * @throws BadNonVirtualParameterTypesException
	 */
	protected void addMethodFamily(MethodHandles.Lookup lookup, Class<?> classInstance, String methodName, Object object) {
		
		// Set all the other methods lastAdded to false.
		for (InvokableMethod m : methods.values()) {
			m.setLastAdded(false);
		}
		
		// Add all the methods.
		java.lang.reflect.Method[] declaredMethods = classInstance.getDeclaredMethods();
		for (java.lang.reflect.Method m : declaredMethods) {
			if (m.getName().equals(methodName)) {
				InvokableMethod newMethod = addMethod(lookup, m, object, null);
				newMethod.setLastAdded(true);
			}
		}
	}
	
	/**
	 * Adds a static method.
	 * @param classInstance the class instance
	 * @param methodName the name of the method
	 * @param parameterTypes the parameter types of the wanted method
	 * @return this instance
	 * @throws MethodInsertionException if the method cannot be found
	 * @throws BadNumberOfVirtualParameterTypesException if the number of virtual parameter types is not equal to the one of the first inserted method
	 * @throws BadNonVirtualParameterTypesException if the non virtual parameter types are not the same than the ones of the first inserted method
	 */
	public MultiMethod<ReturnType> add(Class<?> classInstance, String methodName, Class<?>... parameterTypes) {
		
		// Set all the other methods lastAdded to false.
		for (InvokableMethod m : methods.values()) {
			m.setLastAdded(false);
		}
		
		// Add the method.
		try {
			InvokableMethod newMethod = addMethod(lookup, classInstance.getMethod(methodName, parameterTypes), null, null);
			newMethod.setLastAdded(true);
		}
		catch (ReflectiveOperationException e) {
			throw new MethodInsertionException(e.getMessage());
		}
		
		return this;
	}
	
	/**
	 * Adds a method.
	 * @param object the caller
	 * @param methodName the name of the method
	 * @param parameterTypes the parameter types of the wanted method
	 * @return this instance
	 * @throws MethodInsertionException if the method cannot be found
	 * @throws BadNumberOfVirtualParameterTypesException if the number of virtual parameter types is not equal to the one of the first inserted method
	 * @throws BadNonVirtualParameterTypesException if the non virtual parameter types are not the same than the ones of the first inserted method
	 */
	public MultiMethod<ReturnType> add(Object object, String methodName, Class<?>... parameterTypes) {
		
		// Set all the other methods lastAdded to false.
		for (InvokableMethod m : methods.values()) {
			m.setLastAdded(false);
		}
		
		// Add the method.
		try {
			InvokableMethod newMethod = addMethod(lookup, object.getClass().getMethod(methodName, parameterTypes), object, null);
			newMethod.setLastAdded(true);
		}
		catch (ReflectiveOperationException e) {
			throw new MethodInsertionException(e.getMessage());
		}
		
		return this;
	}
	
	/**
	 * Sets the data for all the last added methods i.e. the methods added during the last call to the <code>add</code> method.
	 * @param data the data
	 * @return this instance
	 */
	public MultiMethod<ReturnType> data(Comparable<?> data) {
		
		// Find the last added methods and set data.
		for (InvokableMethod m : methods.values()) {
			if (m.isLastAdded()) {
				m.setData(data);
			}
		}
		
		return this;
	}
	
	/**
	 * Adds a family of static methods.
	 * @param classInstance the class instance
	 * @param methodName the method name
	 * @return this instance
	 * @throws MethodInsertionException if any method can be found
	 * @throws BadNumberOfVirtualParameterTypesException if the number of virtual parameter types is not equal to the one of the first inserted method
	 * @throws BadNonVirtualParameterTypesException if the non virtual parameter types are not the same than the ones of the first inserted method
	 */
	public MultiMethod<ReturnType> add(Class<?> classInstance, String methodName) {
		
		addMethodFamily(lookup, classInstance, methodName, null);
		
		return this;
	}
	
	/**
	 * Adds a family of methods.
	 * @param object the caller
	 * @param methodName the method name
	 * @return this instance
	 * @throws MethodInsertionException if any method can be found
	 * @throws BadNumberOfVirtualParameterTypesException if the number of virtual parameter types is not equal to the one of the first inserted method
	 * @throws BadNonVirtualParameterTypesException if the non virtual parameter types are not the same than the ones of the first inserted method
	 */
	public MultiMethod<ReturnType> add(Object object, String methodName) {

		addMethodFamily(lookup, object.getClass(), methodName, object);
		
		return this;
	}
	
	/**
	 * Adds the family of methods with the name <code>match</code>.
	 * @param object the caller
	 * @return this instance
	 */
	public MultiMethod<ReturnType> add(Object object) {
		
		return this.add(object, DEFAULT_METHOD_NAME);
	}
	
	/**
	 * Adds the family of methods with the name <code>match</code> defined in the anonymous class deriving <code>Cases</code>.
	 * @param cases the anonymous class defining the methods
	 * @return this instance
	 * @throws MethodInsertionException if a method cannot be inserted
	 * @throws BadNumberOfVirtualParameterTypesException if the number of virtual parameter types is not equal to the one of the first inserted method
	 * @throws BadNonVirtualParameterTypesException if the non virtual parameter types are not the same than the ones of the first inserted method
	 */
	public MultiMethod<ReturnType> add(Cases cases) {
		
		// Get the current lookup.
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		try {
			// Access to the lookup of the anonymous Cases object.
			lookup = CasesLookup.privateLookupIn(cases.getClass(), lookup);
			
			addMethodFamily(lookup, cases.getClass(), "match", cases);
			
		} catch (IllegalAccessException e) {
			throw new MethodInsertionException(e.getMessage());
		}
		
		return this;
	}
	
	/**
	 * Processes the class tuple.
	 * @param args the arguments
	 * @return the matching method
	 * @throws NoMatchingMethodException
	 * @throws AmbiguousMethodException
	 */
	protected InvokableMethod processClassTuple(Object[] args) throws NoMatchingMethodException, AmbiguousMethodException {
		
		// Create a ClassTuple from the arguments.
		Class<?>[] virtualParameterTypes = new Class<?>[getDimension()];
		for (int i = 0; i < getDimension(); ++i) {
			virtualParameterTypes[i] = args[i].getClass();
		}
		ClassTuple methodTuple = new ClassTuple(virtualParameterTypes);
		
		// Set the args to the comparator.
		methodComparator.setArgs(args);
		
		return processClassTuple(methodTuple, SuperClass.calculate(methodTuple));
	}

	/**
	 * Calculates the compatible method with the associated distance tuple.
	 * @param superClassSet the set of super classes
	 * @param method the method
	 * @return the method item
	 */
	protected static MethodItem calculateCompatibleMethod(HashMap<Class<?>, Integer>[] superClassSet, InvokableMethod method) {
		
		Class<?>[] classTuple = method.getClassTuple().get();
		int[] distanceTuple = new int[superClassSet.length];
		
		for (int i = 0; i < superClassSet.length; ++i) {
			
			Integer distance = superClassSet[i].get(classTuple[i]);
			
			if (distance != null) {
				distanceTuple[i] = distance;
			}
			else {
				return null;
			}
		}
		
		return new MethodItem(method, distanceTuple);
	}
	
	/**
	 * Processes the class tuple. This is the main method which selects the "closest" method in terms of arguments using the distance tuple.
	 * The method comparator is used to compare the methods i.e. result varies depending on the implementation of the comparator.
	 * @param tuple the class tuple
	 * @param superClassSet the set of super classes
	 * @return the matching method
	 * @throws NoMatchingMethodException
	 * @throws AmbiguousMethodException
	 */
	private InvokableMethod processClassTuple(ClassTuple tuple, HashMap<Class<?>, Integer>[] superClassSet) throws NoMatchingMethodException, AmbiguousMethodException {
		
		// Search for compatible methods.
		ArrayList<MethodItem> compatibleMethodItems = new ArrayList<MethodItem>();
		
		// Iterate the list of added methods.
		for (InvokableMethod method : methods.values()) {
			
			// Calculate the compatible method with its distance tuple.
			MethodItem item = calculateCompatibleMethod(superClassSet, method);
	
			// Test if the method is applicable.
			if (item != null) {
				// The method is added if it is equals to itself.
				if (methodComparator.compare(item, item) == 0) {
					compatibleMethodItems.add(item);
				}
			}
		}
		
		// Search for the minimum method item among all the compatible methods.
		ArrayList<MethodItem> minMethodItems = new ArrayList<MethodItem>();
		
		// No compatible method.
		if (compatibleMethodItems.isEmpty()) {
			throw new NoMatchingMethodException(tuple);
		}

		// There is at least 1 item at this point.
		Iterator<MethodItem> i = compatibleMethodItems.iterator();
		minMethodItems.add(i.next());
		
		// Search for the minimum.
		while (i.hasNext()) {
			
			MethodItem item = i.next();
			
			// Compare the method item with the existing minimum method item. 
			int result = methodComparator.compare(item, minMethodItems.get(0));
			
			// The method item equals the existing minimum.
			if (result == 0) {
				minMethodItems.add(item);
			}
			// The method is less than the existing minimum method.
			else if (result < 0) {
				// Reset the set and add the method item.
				minMethodItems.clear();
				minMethodItems.add(item);
			}
		}
		
		// Test the result, the container cannot be empty.
		if (minMethodItems.size() == 1) {
			
			// Check that the minimum is really minimum.
			MethodItem minItem = minMethodItems.get(0);
			
			i = compatibleMethodItems.iterator();
			minMethodItems.add(i.next());
			
			// Iterate the minimum to verify.
			while (i.hasNext()) {
				MethodItem item = i.next();
				if (item != minItem && methodComparator.compare(minItem, item) != -1) {
					// minItem is not the real minimum
					throw new NoMatchingMethodException(tuple);
				}
			}
			
			// Return the verified minimum item.
			return minItem;
		}
		
		// Create the string of ambiguous methods and throw the exception.
		String possibleMethods = "";
		for (MethodItem item : minMethodItems) {
			possibleMethods += "\t" + item.getMethod() + "\n";
		}
		
		throw new AmbiguousMethodException(tuple, possibleMethods);
	}

	@Override
	public String toString() {
		return "MultiMethod [dimension=" + dimension + ", methodComparator="
				+ methodComparator.getClass().getCanonicalName() + ", methods="
				+ methods.size() + ", nonVirtualParameterTypes="
				+ Arrays.toString(nonVirtualParameterTypes) + "]";
	}
	
	
}
