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
import java.lang.reflect.Method;

import eu.daproject.evl.util.ClassTuple;

/**
 * Class defining an invokable method for the EVL library as the extension of a {@link MethodHandle} with the caller object, the class tuple of "virtual" arguments and some additional data.
 *
 */
public class InvokableMethod {

	private Method method;
	private MethodHandle methodHandle;
	private Object object;
	private ClassTuple tuple;
	private Comparable<?> data;
	private boolean lastAdded = false;
	
	/**
	 * Constructs a method.
	 * @param tuple the class tuple
	 * @param method the reflective method
	 * @param methodHandle the method handle
	 * @param object the caller object
	 */
	InvokableMethod(ClassTuple tuple, Method method, MethodHandle methodHandle, Object object) {
		this.method = method;
		this.methodHandle = methodHandle;
		this.object = object;
		this.tuple = tuple;
	}
	
	/**
	 * Sets last added.
	 * @param value
	 */
	void setLastAdded(boolean value) {
		lastAdded = value;
	}
	
	/**
	 * Returns true if is last added.
	 * @return true if is last added
	 */
	boolean isLastAdded() {
		return lastAdded;
	}
	
	/**
	 * Gets the reflective method.
	 * @return the method handle
	 */
	public Method getMethod() {
		return method;
	}
	
	/**
	 * Gets the method handle.
	 * @return the method handle
	 */
	public MethodHandle getMethodHandle() {
		return methodHandle;
	}
	
	/**
	 * Gets the caller object.
	 * @return the caller object
	 */
	public Object getObject() {
		return object;
	}
	
	/**
	 * Gets the class tuple.
	 * @return the class tuple
	 */
	public ClassTuple getClassTuple() {
		return tuple;
	}

	/**
	 * Sets the associated data e.g. a {@link Priority} object.
	 * @param data the associated data
	 */
	public void setData(Comparable<?> data) {
		this.data = data;
	}
	
	/**
	 * Gets the associated data.
	 * @return the associated data
	 */
	public Comparable<?> getData() {
		return data;
	}
	
}
