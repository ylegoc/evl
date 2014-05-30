package org.bitbucket.evl.util;

import java.util.HashMap;

import org.bitbucket.evl.data.DispatchableMethodD;
import org.bitbucket.evl.data.MethodItemD;

public class CompatibleMethod {

	public static <ReturnType, DataType> MethodItemD<DataType> calculate(HashMap<Class<?>, Integer>[] superClassSet, DispatchableMethodD<DataType> method) {
		
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
		
		return new MethodItemD<DataType>(method, distanceTuple);
	}
}
