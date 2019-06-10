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

/**
 * Class providing a base class for the method comparators.
 * The arguments passed to the calling invoke are set at thread-local and can be used for predicate based dispatch where there is no cache.
 */
public abstract class MethodComparator {

	private ThreadLocal<Object[]> threadLocalArgs = new ThreadLocal<Object[]>();
	
	/**
	 * Gets the arguments.
	 * @return the arguments
	 */
	public Object[] args() {
		return threadLocalArgs.get();
	}

	/**
	 * Sets the arguments.
	 * @param args the arguments
	 */
	void setArgs(Object[] args) {
		threadLocalArgs.set(args);
	}
	
	/**
	 * Compares two {@link MethodItem} objects.
	 * The method must be implemented in the concrete method comparator.
	 * @param m1 the first method item
	 * @param m2 the second method item
	 * @return -1, zero, or 1 as <code>m1</code> is less than, equal to, or greater than <code>m2</code>
	 */
	public abstract int compare(MethodItem m1, MethodItem m2);
	
}
