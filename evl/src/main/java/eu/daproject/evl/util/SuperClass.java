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

import java.util.HashMap;

public class SuperClass {

	public static HashMap<Class<?>, Integer>[] calculate(ClassTuple tuple) {
		
		@SuppressWarnings("unchecked")
		HashMap<Class<?>, Integer>[] result = new HashMap[tuple.get().length];
		
		for (int i = 0; i < tuple.get().length; ++i) {
			result[i] = calculate(tuple.get()[i]);
		}
		
		return result;
	}

	public static HashMap<Class<?>, Integer> calculate(Class<?> classInstance) {
		
		HashMap<Class<?>, Integer> result = new HashMap<Class<?>, Integer>();
		
		result.put(classInstance, 0);
		
		getSuperClassesAndInterfaces(classInstance, result, 1);
		
		return result;
	}
	
	private static void getSuperClassesAndInterfaces(Class<?> classInstance, HashMap<Class<?>, Integer> classDistanceMap, int distance) {
	
		// super class
		Class<?> superClass = classInstance.getSuperclass();
		
		if (superClass != null) {
			processSuperClass(classDistanceMap, superClass, distance);
		}
		
		// interfaces
		Class<?>[] interfaces = classInstance.getInterfaces();
		
		for (int i = 0; i < interfaces.length; ++i) {
			processSuperClass(classDistanceMap, interfaces[i], distance);
		}
	}
	
	private static void processSuperClass(HashMap<Class<?>, Integer> classDistanceMap, Class<?> superClass, int distance) {
		// search super class in map
		Integer superClassDistance = classDistanceMap.get(superClass);
		if (superClassDistance == null) {
			
			classDistanceMap.put(superClass, distance);
			
		} else {
			// update distance
			if (superClassDistance < distance) {
				superClassDistance = distance;
			}
		}
		
		getSuperClassesAndInterfaces(superClass, classDistanceMap, distance + 1);
	}
}
