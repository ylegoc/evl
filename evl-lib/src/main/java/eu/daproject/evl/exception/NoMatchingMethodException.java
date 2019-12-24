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
package eu.daproject.evl.exception;

import eu.daproject.evl.util.ClassTuple;

/**
 * Exception when there is no matching method available in the multimethod.
 *
 */
public class NoMatchingMethodException extends InvocationException {

	private static final long serialVersionUID = 7L;
	
	/**
	 * Constructs an exception with the class tuple.
	 * @param classTuple the class tuple
	 */
	public NoMatchingMethodException(ClassTuple classTuple, String message) {
		super("No matching method for " + classTuple + ": " + message);
	}
}
