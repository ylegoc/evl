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
import eu.daproject.evl.exception.AmbiguousMethodExceptionThrower;
import eu.daproject.evl.exception.BadNonVirtualParameterTypesException;
import eu.daproject.evl.exception.BadNumberOfVirtualParameterTypesException;
import eu.daproject.evl.exception.BadReturnTypeException;
import eu.daproject.evl.exception.ExceptionThrower;
import eu.daproject.evl.exception.InvocationException;
import eu.daproject.evl.exception.MethodNotAddedException;
import eu.daproject.evl.exception.NoMatchingMethodException;
import eu.daproject.evl.exception.NoMatchingMethodExceptionThrower;
import eu.daproject.evl.exception.UnexpectedException;
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
	
	/**
	 * Class helper to group synchronized methods on members. 
	 *
	 */
	protected static class SynchronizedMembers {
	
		private MethodComparator methodComparator;
		private boolean isFinal = false;
		private boolean isOverridable = true;
		private HashMap<ClassTuple, InvokableMethod> methods = new HashMap<ClassTuple, InvokableMethod>();
		private Class<?>[] nonVirtualParameterTypes;
		private Class<?> returnType;
		
		synchronized void setMethodComparator(MethodComparator methodComparator) {
			this.methodComparator = methodComparator;	
		}
		
		synchronized MethodComparator getComparator() {
			return methodComparator;
		}
		
		synchronized void setOverridable(boolean value) {
			this.isOverridable = value;
		}
		
		synchronized boolean isOverridable() {
			return this.isOverridable;
		}
		
		synchronized void setFinal() {
			this.isFinal = true;
		}
		
		synchronized boolean isFinal() {
			return this.isFinal;
		}
		
		synchronized void checkMethodTuple(ClassTuple tuple) {
			
			if (isFinal) {
				throw new MethodNotAddedException("MultiMethod is final");
			}
			
			if (methods.containsKey(tuple) && !isOverridable) {
				throw new MethodNotAddedException("MultiMethod is not overridable");
			}
		}
		
		synchronized void addMethod(ClassTuple tuple, InvokableMethod method) {
			method.setLastAdded(true);
			methods.put(tuple, method);
		}
		
		synchronized void setLastAddedToMethods() {
			for (InvokableMethod m : methods.values()) {
				m.setLastAdded(false);
			}
		}
		
		synchronized HashMap<ClassTuple, InvokableMethod> getMethods() {
			return methods;
		}
		
		synchronized Collection<InvokableMethod> getMethodsValues() {
			return methods.values();
		}
		
		synchronized void setData(Comparable<?> data, Class<?>... tuple) {
			methods.get(new ClassTuple(tuple)).setData(data);
		}
		
		synchronized void setDataToMethodsIfLastAdded(Comparable<?> data) {
			for (InvokableMethod m : methods.values()) {
				if (m.isLastAdded()) {
					m.setData(data);
				}
			}
		}
		
		synchronized void setNonVirtualParameterTypes(Class<?>...types) {
			if (this.nonVirtualParameterTypes == null) {
				nonVirtualParameterTypes = types;
			}
		}
		
		synchronized void updateNonVirtualParameterTypes(Class<?>...types) {
			if (nonVirtualParameterTypes == null) {
				nonVirtualParameterTypes = types;
			}
			else {
				// Check the equality with the non virtual parameter types of the first inserted method.
				if (!Arrays.equals(nonVirtualParameterTypes, types)) {
					throw new BadNonVirtualParameterTypesException(new ClassTuple(nonVirtualParameterTypes).toString(), new ClassTuple(types).toString());
				}
			}
		}
		
		synchronized Class<?>[] getNonVirtualParameterTypes() {
			return nonVirtualParameterTypes;
		}
		
		synchronized void setReturnType(Class<?> type) {
			if (this.returnType == null) {
				this.returnType = type;
			}
		}
		
		synchronized void updateReturnType(Class<?> type) {
			if (returnType == null) {
				returnType = type;
			}
			else {
				if (!returnType.equals(type)) {
					// void can come from a match method.
					if (returnType.equals(Void.class)) {
						if (type.equals(void.class)) {
							// Void and void are compatible.
							return;
						}
					}
					
					// Types are different, check if they are compatible.
					if (!returnType.isAssignableFrom(type)) {
						throw new BadReturnTypeException(returnType, type);
					}
				}
			}
		}
		
		synchronized Class<?> getReturnType() {
			return returnType;
		}
	}
	
	private SynchronizedMembers syncThis = new SynchronizedMembers();
	
	/**
	 * Constructs a multimethod with the dimension and a method comparator.
	 * @param dimension the number of "virtual" parameters
	 * @param methodComparator the method comparator
	 */
	MultiMethod(int dimension, MethodComparator methodComparator) {
		this.dimension = dimension;
		syncThis.setMethodComparator(methodComparator);
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
		return syncThis.getMethodsValues();
	}
	
	/**
	 * Sets the associated data e.g. a {@link Priority} object of the method.
	 * Resets the cache.
	 * @param data the associated data
	 * @param tuple the tuple of types
	 */
	public void setData(Comparable<?> data, Class<?>... tuple) {
		clearCache();
		syncThis.setData(data, tuple);
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
		syncThis.setMethodComparator(methodComparator);
		return this;
	}
	
	/**
	 * Sets the symmetric method comparator.
	 * @return this instance
	 */
	public MultiMethod<ReturnType> symmetricComparator() {
		syncThis.setMethodComparator(new SymmetricComparator());
		return this;
	}
	
	/**
	 * Gets the method comparator.
	 * @return the method comparator
	 */
	public MethodComparator getComparator() {
		return syncThis.getComparator();
	}
	
	/**
	 * Sets the non virtual parameter types. If not called, the non-virtual parameter types of the first added method are set.
	 * If the non-virtual parameter types are already set, the call has no effect.
	 * @param types the non-virtual parameter types
	 * @return this instance
	 */
	public MultiMethod<ReturnType> nonVirtualParameterTypes(Class<?>...types) {
		syncThis.setNonVirtualParameterTypes(types);
		return this;
	}
	
	/**
	 * Gets the non-virtual parameter types.
	 * @return the array of non-virtual parameter types
	 */
	public Class<?>[] getNonVirtualParameterTypes() {
		return syncThis.getNonVirtualParameterTypes();
	}
	
	/**
	 * Sets the return type. If not called, the return type of the first added method is set.
	 * If the return type is already set, the call has no effect.
	 * @param type the return type
	 * @return this instance
	 */
	public MultiMethod<ReturnType> returnType(Class<?> type) {
		syncThis.setReturnType(type);
		return this;
	}

	/**
	 * Gets the return type.
	 * @return the return type
	 */
	public Class<?> getReturnType() {
		return syncThis.getReturnType();
	}
	
	/**
	 * Gives access to the private and protected methods of the class.
	 * @param classInstance the class instance
	 */
	public void access(Class<?> classInstance) {
		// This call could be done automatically in add but that would it change lookup too often?
		try {
			lookup = MethodHandles.privateLookupIn(classInstance, lookup);
		}
		catch (IllegalAccessException e) {
		}
	}
	
	/**
	 * Sets the overridable parameter. It is not possible to add method if a method already exists for the class tuple.
	 * @return this instance
	 */
	public MultiMethod<ReturnType> notOverridable() {
		syncThis.setOverridable(false);
		return this;
	}
	
	/**
	 * Gets the overridable parameter.
	 * @return true if the instance is overridable
	 */
	public boolean isOverridable() {
		return syncThis.isOverridable();
	}
	
	/**
	 * Sets the final parameter.
	 */
	public void setFinal() {
		syncThis.setFinal();
	}
	
	/**
	 * Gets the final parameter.
	 * @return true if the instance is final
	 */
	public boolean isFinal() {
		return syncThis.isFinal();
	}
	
	/**
	 * Adds a method with the caller and the associated lookup and some optional data.
	 * @param lookup the lookup
	 * @param method the method
	 * @param object the caller
	 * @param data the data
	 * @return the inserted method
	 * @throws MethodNotAddedException
	 * @throws BadNumberOfVirtualParameterTypesException
	 * @throws BadNonVirtualParameterTypesException
	 */
	protected InvokableMethod addMethod(MethodHandles.Lookup lookup, java.lang.reflect.Method method, Object object, Comparable<?> data) {
		
		// The method is entirely synchronized to avoid strange behaviors if two threads add a method. 
		synchronized(this) {
		
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
					throw new MethodNotAddedException(e.getMessage());
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
			
			// Create the class tuple.
			ClassTuple tuple = new ClassTuple(newVirtualParameterTypes);
			
			// Check if the method can be added.
			syncThis.checkMethodTuple(tuple);
			
			// Update the non virtual parameter types if this is the first inserted method.
			syncThis.updateNonVirtualParameterTypes(newNonVirtualParameterTypes);
			
			// Check the return type.
			syncThis.updateReturnType(method.getReturnType());
			
			// The cache must be cleared.
			clearCache();
	
			// Add the new method.
			InvokableMethod newMethod = new InvokableMethod(tuple, method, methodHandle, object);
			newMethod.setData(data);
			syncThis.addMethod(tuple, newMethod);
			
			return newMethod;
		}
	}
	
	/**
	 * Adds a family of methods with the caller.
	 * @param lookup the lookup
	 * @param classInstance the class instance
	 * @param methodName the method name, all the overloaded methods with this name are added
	 * @param object the caller
	 * @throws UnexpectedException
	 * @throws BadNumberOfVirtualParameterTypesException
	 * @throws BadNonVirtualParameterTypesException
	 */
	protected void addMethodFamily(MethodHandles.Lookup lookup, Class<?> classInstance, String methodName, Object object) {
		
		// Set all the other methods lastAdded to false.
		syncThis.setLastAddedToMethods();
		
		// Get the list of classes.
		ArrayList<Class<?>> classInstances = new ArrayList<>();
		Class<?> superClass = classInstance;

		while (superClass != null) {
			classInstances.add(superClass);
			superClass = superClass.getSuperclass();
		}
		
		// Add all the methods.
		for (Class<?> c : classInstances) {
			java.lang.reflect.Method[] declaredMethods = c.getDeclaredMethods();
			for (java.lang.reflect.Method m : declaredMethods) {
				if (m.getName().equals(methodName)) {
					addMethod(lookup, m, object, null);
				}
			}
		}
	}
	
	/**
	 * Adds a static method.
	 * @param classInstance the class instance
	 * @param methodName the name of the method
	 * @param parameterTypes the parameter types of the wanted method
	 * @return this instance
	 * @throws MethodNotAddedException if the method cannot be found
	 * @throws BadNumberOfVirtualParameterTypesException if the number of virtual parameter types is not equal to the one of the first inserted method
	 * @throws BadNonVirtualParameterTypesException if the non virtual parameter types are not the same than the ones of the first inserted method
	 */
	public MultiMethod<ReturnType> add(Class<?> classInstance, String methodName, Class<?>... parameterTypes) {
		
		// Set all the other methods lastAdded to false.
		syncThis.setLastAddedToMethods();
		
		// Add the method.
		try {
			addMethod(lookup, classInstance.getMethod(methodName, parameterTypes), null, null);
		}
		catch (ReflectiveOperationException e) {
			throw new MethodNotAddedException(e.getMessage());
		}
		
		return this;
	}
	
	/**
	 * Adds a method.
	 * @param object the caller
	 * @param methodName the name of the method
	 * @param parameterTypes the parameter types of the wanted method
	 * @return this instance
	 * @throws MethodNotAddedException if the method cannot be found
	 * @throws BadNumberOfVirtualParameterTypesException if the number of virtual parameter types is not equal to the one of the first inserted method
	 * @throws BadNonVirtualParameterTypesException if the non virtual parameter types are not the same than the ones of the first inserted method
	 */
	public MultiMethod<ReturnType> add(Object object, String methodName, Class<?>... parameterTypes) {
		
		// Set all the other methods lastAdded to false.
		syncThis.setLastAddedToMethods();
		
		// Add the method.
		try {
			addMethod(lookup, object.getClass().getMethod(methodName, parameterTypes), object, null);
		}
		catch (ReflectiveOperationException e) {
			throw new MethodNotAddedException(e.getMessage());
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
		syncThis.setDataToMethodsIfLastAdded(data);

		return this;
	}
	
	/**
	 * Adds a family of static methods.
	 * @param classInstance the class instance
	 * @param methodName the method name
	 * @return this instance
	 * @throws UnexpectedException if any method can be found
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
	 * @throws UnexpectedException if any method can be found
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
	 * @throws MethodNotAddedException if a method cannot be inserted
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
			throw new MethodNotAddedException(e.getMessage());
		}
		
		return this;
	}
	
	/**
	 * Verifies that the virtual arguments are non-null.
	 * @param args
	 */
	protected void checkVirtualArgs(Object... args) {
		
		// Iterate the arguments.
		for (Object arg : args) {
			if (arg == null) {
				throw new IllegalArgumentException("A virtual argument cannot be null");
			}
		}
	}
	
	/**
	 * Processes the class tuple.
	 * @param args the arguments
	 * @return the matching method
	 * @throws NoMatchingMethodException
	 * @throws AmbiguousMethodException
	 */
	protected InvokableMethod processClassTuple(Object[] args) {
		
		// Create a ClassTuple from the arguments.
		Class<?>[] virtualParameterTypes = new Class<?>[getDimension()];
		for (int i = 0; i < getDimension(); ++i) {
			virtualParameterTypes[i] = args[i].getClass();
		}
		ClassTuple methodTuple = new ClassTuple(virtualParameterTypes);
		
		// Set the args to the comparator.
		syncThis.getComparator().setArgs(args);
		
		return processClassTuple(methodTuple, SuperClass.calculate(methodTuple));
	}
	
	protected void checkClassTuple(Class<?>... classes) throws InvocationException {
		
		ClassTuple methodTuple = new ClassTuple(classes);
		
		InvokableMethod method = processClassTuple(methodTuple, SuperClass.calculate(methodTuple));
		
		Object object = method.getObject();
		if (object instanceof ExceptionThrower) {
			((ExceptionThrower)object).invoke();
		}
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
	private InvokableMethod processClassTuple(ClassTuple tuple, HashMap<Class<?>, Integer>[] superClassSet) {
		
		// Search for compatible methods.
		ArrayList<MethodItem> compatibleMethodItems = new ArrayList<MethodItem>();
		
		// Get the methods.
		Collection<InvokableMethod> methods = syncThis.getMethodsValues();
		
		// Get the comparator.
		MethodComparator methodComparator = syncThis.getComparator();
		
		// Iterate the list of added methods.
		for (InvokableMethod method : methods) {
			
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
			// Create the thrower and return the method handle to be cached.
			NoMatchingMethodExceptionThrower thrower = new NoMatchingMethodExceptionThrower(lookup, tuple, "There is no compatible method");
			
			// Return the invokable method.
			return new InvokableMethod(tuple, thrower.getMethod(), thrower.getMethodHandle(), thrower);
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
					// minItem is not the real minimum.
					// Create the thrower and return the method handle to be cached.
					NoMatchingMethodExceptionThrower thrower = new NoMatchingMethodExceptionThrower(lookup, tuple, "There is no minimum method, the comparator is not consistant");
					
					// Return the invokable method.
					return new InvokableMethod(tuple, thrower.getMethod(), thrower.getMethodHandle(), thrower);
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

		// Create the thrower and return the method handle to be cached.
		AmbiguousMethodExceptionThrower thrower = new AmbiguousMethodExceptionThrower(lookup, tuple, possibleMethods);
		
		// Return the invokable method.
		return new InvokableMethod(tuple, thrower.getMethod(), thrower.getMethodHandle(), thrower);
	}

	@Override
	public String toString() {
		
		MethodComparator methodComparator = syncThis.getComparator();
		Class<?>[] nonVirtualParameterTypes = syncThis.getNonVirtualParameterTypes();
		
		return "MultiMethod [dimension=" + dimension + ", methodComparator="
				+ methodComparator.getClass().getCanonicalName() + ", nonVirtualParameterTypes="
				+ Arrays.toString(nonVirtualParameterTypes) + "]";
	}
	
	
}
