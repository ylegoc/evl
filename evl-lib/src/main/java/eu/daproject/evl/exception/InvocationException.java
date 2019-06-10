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

/**
 * Base exception class for the EVL library.
 *
 */
public class InvocationException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an exception with the message.
	 * @param message the message of the exception
	 */
	public InvocationException(String message) {
		super(message);
	}
}
