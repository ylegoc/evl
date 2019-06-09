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
package eu.daproject.evl.util;

import java.util.Arrays;

/**
 * Class representing a class tuple by encapsulating an array of {@link java.lang.Class}.
 * The class tuple is used to represent the "virtual" parameters of the multimethods.
 * 
 */
public class ClassTuple {

	private Class<?>[] tuple;
	
	/**
	 * Constructs a class tuple with a variable number of classes.
	 * @param tuple the class objects.
	 */
	public ClassTuple(Class<?>... tuple) {
		this.tuple = tuple;
	}
	
	/**
	 * Returns the array of class objects.
	 */
	public Class<?>[] get() {
		return tuple;
	}

	/**
	 * Returns the hash code based on the array of objects.
	 */
	@Override
	public int hashCode() {
		return Arrays.hashCode(tuple);
	}

	/**
	 * Redefines the equals method for the array of class objects.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}

		ClassTuple other = (ClassTuple) obj;
		if (!Arrays.equals(tuple, other.tuple)) {
			return false;
		}
		
		return true;
	}

	/**
	 * Redefines the toString method to print the tuple.
	 */
	@Override
	public String toString() {
		String result = "<";
		
		for (int i = 0; i < tuple.length - 1; i++) {
			result += tuple[i].getName() + ", ";
		}
		
		// Tuple has size > 0.
		result += tuple[tuple.length - 1].getName() + ">";
		
		return result;
	}
	
}
