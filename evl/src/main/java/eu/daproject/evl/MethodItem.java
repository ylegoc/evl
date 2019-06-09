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
 * Class defining a method item used by the search algorithm of the matching method.
 * It extends the {@link Method} class by adding a distance tuple.
 *
 */
public class MethodItem extends Method {

	private int[] distance;
	
	/**
	 * Constructs a method item.
	 * @param method the method.
	 * @param distance the distance.
	 */
	MethodItem(Method method, int[] distance) {
		super(method.getClassTuple(), method.getMethod(), method.getObject());
		setData(method.getData());
		
		this.distance = distance;
	}
	
	/**
	 * Gets the distance tuple.
	 * @return the distance tuple.
	 */
	public int[] getDistanceTuple() {
		return distance;
	}

}
