package evl.base;

import java.lang.reflect.Method;
import java.util.AbstractMap;

import evl.data.DispatchableMethodD;
import evl.data.BaseMethod3D;
import evl.data.MethodComparatorD;
import evl.exceptions.BadNonVirtualParameterTypesException;
import evl.exceptions.BadNumberOfVirtualParameterTypesException;


public class Method3<ReturnType> extends BaseMethod3D<ReturnType, Void> {
	
	public Method3(MethodComparatorD<Void> methodComparator, AbstractMap<ClassTuple, DispatchableMethodD<Void>> cacheMap) {
		super(methodComparator, cacheMap);
	}
		
	public void add(Method method, Object object) throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException {
		super.add(method, object, null);
	}
}
