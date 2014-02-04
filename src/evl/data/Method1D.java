package evl.data;

import java.lang.reflect.Method;
import java.util.AbstractMap;

import evl.exceptions.BadNonVirtualParameterTypesException;
import evl.exceptions.BadNumberOfVirtualParameterTypesException;


public class Method1D<ReturnType, DataType> extends BaseMethod1D<ReturnType, DataType> {
	
	public Method1D(MethodComparatorD<DataType> methodComparator, AbstractMap<Class<?>, DispatchableMethodD<DataType>> cacheMap) {
		super(methodComparator, cacheMap);
	}	
	
	public void add(Method method, Object object, DataType data) throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException {
		super.add(method, object, data);
	}
		
}
