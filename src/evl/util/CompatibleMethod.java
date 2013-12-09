package evl.util;

import java.util.HashMap;

import evl.DispatchableMethod;
import evl.MethodItem;

public class CompatibleMethod {

	public static <ReturnType, DataType> MethodItem<DataType> calculate(HashMap<Class<?>, Integer>[] superClassSet, DispatchableMethod<DataType> method) {
		
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
		
		return new MethodItem<DataType>(method, distanceTuple);
	}
}
