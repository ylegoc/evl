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

/**
 * Helper class to calculate the tuple of super classes of a class tuple.
 *
 */
public class SuperClass {

	/**
	 * Calculates the super classes of a tuple of class with their distance.
	 * @param tuple the class tuple.
	 * @return the array of super classes with their distance - number of classes separating - to each element of the tuple.
	 */
	public static HashMap<Class<?>, Integer>[] calculate(ClassTuple tuple) {
		
		@SuppressWarnings("unchecked")
		HashMap<Class<?>, Integer>[] result = new HashMap[tuple.get().length];
		
		for (int i = 0; i < tuple.get().length; ++i) {
			result[i] = calculate(tuple.get()[i]);
		}
		
		return result;
	}

	/**
	 * Calculates the super classes of a class with their distance.
	 * @param classInstance the class object.
	 * @return the map of super classes with their distance to the class.
	 */
	public static HashMap<Class<?>, Integer> calculate(Class<?> classInstance) {
		
		HashMap<Class<?>, Integer> result = new HashMap<Class<?>, Integer>();
		
		result.put(classInstance, 0);
		
		getSuperClassesAndInterfaces(classInstance, result, 1);
		
		return result;
	}
	
	/**
	 * Gets the super classes and interfaces.
	 * @param classInstance the class object.
	 * @param classDistanceMap the map of class object with the distance.
	 * @param distance the distance.
	 */
	private static void getSuperClassesAndInterfaces(Class<?> classInstance, HashMap<Class<?>, Integer> classDistanceMap, int distance) {
	
		// Process the super class.
		Class<?> superClass = classInstance.getSuperclass();
		
		if (superClass != null) {
			processSuperClass(classDistanceMap, superClass, distance);
		}
		
		// Process the interfaces.
		Class<?>[] interfaces = classInstance.getInterfaces();
		
		for (int i = 0; i < interfaces.length; ++i) {
			processSuperClass(classDistanceMap, interfaces[i], distance);
		}
	}
	
	/**
	 * Process the super class of a class.
	 * @param classDistanceMap the map of class object with the distance
	 * @param superClass the super class object
	 * @param distance the distance
	 */
	private static void processSuperClass(HashMap<Class<?>, Integer> classDistanceMap, Class<?> superClass, int distance) {
		
		// Search the super class in the map.
		Integer superClassDistance = classDistanceMap.get(superClass);
		
		if (superClassDistance == null) {
			// Put it in the map if it is not already in.
			classDistanceMap.put(superClass, distance);
		}
		else {
			// Update the distance i.e. there is a smaller path joining the two classes.
			if (superClassDistance < distance) {
				superClassDistance = distance;
			}
		}
		
		// Continue with the distance + 1.
		getSuperClassesAndInterfaces(superClass, classDistanceMap, distance + 1);
	}
}
