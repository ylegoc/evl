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
 * Exception for the ambiguous method resolution when there are at least two matching methods. 
 *
 */
public class AmbiguousMethodException extends InvocationException {

	private static final long serialVersionUID = 5L;
	
	/**
	 * Constructs an exception by defining the message from the tuple and the possible methods.
	 * @param tuple the tuple.
	 * @param possibleMethods the string of possible methods.
	 */
	public AmbiguousMethodException(ClassTuple tuple, String possibleMethods) {
		super("Ambiguity for class tuple " + tuple + ", possible match methods are\n" + possibleMethods);
	}
}