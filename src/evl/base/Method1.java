package evl.base;

import java.lang.reflect.Method;
import java.util.AbstractMap;

import evl.data.DispatchableMethodD;
import evl.data.BaseMethod1D;
import evl.data.MethodComparatorD;
import evl.exceptions.BadNonVirtualParameterTypesException;
import evl.exceptions.BadNumberOfVirtualParameterTypesException;


public class Method1<ReturnType> extends BaseMethod1D<ReturnType, Void> {
	
	public Method1(MethodComparatorD<Void> methodComparator, AbstractMap<Class<?>, DispatchableMethodD<Void>> cacheMap) {
		super(methodComparator, cacheMap);
	}
	
	public void add(Method method, Object object) throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException {
		super.add(method, object, null);
	}
}
