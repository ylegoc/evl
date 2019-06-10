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
 * Class defining a switch multimethod. This is a syntactic sugar for the {@link Method1} with <code>void</code> as return type. 
 *
 */
public class Switch extends Method1<Void> {

	/**
	 * Constructs a switch instance with an anonymous {@link Cases} object defining the <code>match</code> methods.
	 * @param cases the anonymous {@link Cases} object 
	 * @return a new instance with the <code>match</code> methods inserted
	 */
	public static Switch with(Cases cases) {
		return (Switch)new Switch().add(cases);
	}
}
