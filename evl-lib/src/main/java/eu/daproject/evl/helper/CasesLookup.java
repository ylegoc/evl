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
package eu.daproject.evl.helper;

import java.lang.invoke.MethodHandles;

/**
 * Class implementing the private lookup in the method handles.
 * It is simply calling the Java 9 <code>MethodHandles.privateLookupIn</code> method.
 *
 */
public class CasesLookup {

	/**
	 * Returns the <code>MethodHandles.privateLookupIn</code> result available since Java 9.
	 * @param classInstance the class instance
	 * @param lookup the lookup
	 * @return the new lookup
	 * @throws IllegalAccessException if the access check specified above fails
	 * @throws SecurityException if denied by the security manager
	 */
	public static MethodHandles.Lookup privateLookupIn(Class<?> classInstance, MethodHandles.Lookup lookup) throws IllegalAccessException, SecurityException {
		return MethodHandles.privateLookupIn(classInstance.getEnclosingClass(), lookup);
	}
}
