package org.bitbucket.evl;

import java.util.HashMap;

public class CompatibleMethod {

	public static MethodItem calculate(HashMap<Class<?>, Integer>[] superClassSet, DispatchableMethod method) {
		
		Class<?>[] classTuple = method.getClassTuple().get();
		int[] distanceTuple = new int[superClassSet.length];
		
		for (int i = 0; i < superClassSet.length; ++i) {
			
			Integer distance = superClassSet[i].get(classTuple[i]);
			
			if (distance != null) {
				distanceTuple[i] = distance;
				
			} else {
				return null;
			}
		}
		
		return new MethodItem(method, distanceTuple);
	}
}
