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
 * Class defining a priority used for make non ambiguous calls.
 * It is typically used to prefer a method over others when a multimethod call leads to an ambiguity.
 * It is used by setting the data associated to a method.
 * Example:
 * <pre>
 *    Method1<Void> foo = new Method1<Void>().add(obj, "foo", A.class).data(Priority.valueOf(2));
 * </pre>
 *
 */
public class Priority implements Comparable<Priority> {

	private int value;
	
	/**
	 * Constructs a priority object with the value.
	 * @param value
	 */
	protected Priority(int value) {
		this.value = value;
	}
	
	/**
	 * Returns a priority object with the value.
	 * @param value the value
	 * @return the priority object
	 */
	public static Priority valueOf(int value) {
		return new Priority(value);
	}
	
	/**
	 * Compares two priority objects by comparing their values.
	 */
	@Override
	public int compareTo(Priority other) {
		
		if (other == null) {
			return 1;
		}
		
		return Integer.valueOf(value).compareTo(Integer.valueOf(other.value));
	}

}
