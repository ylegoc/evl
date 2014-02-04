package evl.base;

import java.lang.reflect.Method;
import java.util.AbstractMap;

import evl.data.DispatchableMethodD;
import evl.data.BaseMethod4D;
import evl.data.MethodComparatorD;
import evl.exceptions.BadNonVirtualParameterTypesException;
import evl.exceptions.BadNumberOfVirtualParameterTypesException;


public class Method4<ReturnType> extends BaseMethod4D<ReturnType, Void> {
	
	public Method4(MethodComparatorD<Void> methodComparator, AbstractMap<ClassTuple, DispatchableMethodD<Void>> cacheMap) {
		super(methodComparator, cacheMap);
	}
		
	public void add(Method method, Object object) throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException {
		super.add(method, object, null);
	}
}
