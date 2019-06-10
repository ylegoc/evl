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
package eu.daproject.evl.predicate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import eu.daproject.evl.exception.MethodInsertionException;

/**
 * Class defining a predicate for the predicate multimethods.
 *
 */
public class Predicate implements Comparable<Predicate> {

	/**
	 * Class defining an invocation exception for the {@link Predicate} class.
	 *
	 */
	static public class InvocationException extends RuntimeException {

		private static final long serialVersionUID = 6876280226858995852L;

		/**
		 * Constructs an exception.
		 */
		public InvocationException() {
			super("Predicate invocation exception");
		}
	}
	
	private Method method;
	private Object object;
	private ThreadLocal<Object[]> threadLocalArgs = new ThreadLocal<Object[]>();
	
	/**
	 * Creates a predicate with the associated method.
	 * @param object the caller
	 * @param methodName the method name
	 * @param parameterTypes the parameter types
	 * @throws MethodInsertionException if the method cannot be found
	 */
	public Predicate(Object object, String methodName, Class<?>... parameterTypes) {
		
		try {
			this.method = object.getClass().getMethod(methodName, parameterTypes);
			this.method.setAccessible(true);
			
			this.object = object;	
		}
		catch (NoSuchMethodException | SecurityException e) {
			throw new MethodInsertionException(e.getMessage());
		}
	}
	
	/**
	 * Sets the arguments.
	 * @param args the arguments
	 */
	void setArgs(Object[] args) {
		threadLocalArgs.set(args);
	}
	
	/**
	 * throws InvocationException if the method cannot be invoked
	 */
	@Override
	public int compareTo(Predicate other) {
		
		if (other == null) {
			return 1;
		}
		
		try {
			Boolean thisValue = (Boolean)method.invoke(object, threadLocalArgs.get());
			Boolean otherValue = (Boolean)other.method.invoke(other.object, threadLocalArgs.get());
		
			return thisValue.compareTo(otherValue);
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new InvocationException();
		}
	}

}
