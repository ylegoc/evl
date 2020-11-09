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
	 * Compares two {@link MethodItem} objects with priority after the tuple comparison.
	 * This is a helper method to define easily the real <code>compare</code> methods.
	 * @param m1
	 * @param m2
	 * @param tupleComparison
	 * @return -1, zero, or 1 as <code>m1</code> is less than, equal to, or greater than <code>m2</code>
	 */
	protected int compareWithPriority(MethodItem m1, MethodItem m2, int tupleComparison) {
		
		// Compare the distance tuple of each multimethod.
		if (tupleComparison == 0) {
			Priority priority1 = null;
			if (m1.getData() == null) {
				priority1 = Priority.valueOf(0);
			}
			else if (m1.getData() instanceof Priority) {
				priority1 = (Priority)m1.getData();
			}
			
			Priority priority2 = null;
			if (m2.getData() == null) {
				priority2 = Priority.valueOf(0);
			}
			else if (m2.getData() instanceof Priority) {
				priority2 = (Priority)m2.getData();
			}
			
			if (priority1 != null) {
				// We search for the "closest" tuple, so if we want to choose m1 if it has greater priority i.e. m1 < m2, 
				// then we need to invert the result. 
				return -priority1.compareTo(priority2);
			}
			
			if (priority2 != null) {
				// We let the result because we compare priority2 < priority1.
				return priority2.compareTo(priority1);
			}
		}
		
		return tupleComparison;
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
