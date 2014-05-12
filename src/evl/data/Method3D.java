package evl.data;

import java.lang.reflect.Method;
import java.util.Map;

import evl.exceptions.BadNonVirtualParameterTypesException;
import evl.exceptions.BadNumberOfVirtualParameterTypesException;
import evl.util.CacheFactory;


public class Method3D<ReturnType, DataType> extends BaseMethod3D<ReturnType, DataType> {
	
	public Method3D<ReturnType, DataType> add(Method method, Object object, DataType data) throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException {
		super.addMethod(method, object, data);
		return this;
	}
	
	public Method3D<ReturnType, DataType> comparator(MethodComparatorD<DataType> methodComparator) {
		this.methodComparator = methodComparator;
		return this;
	}
	
	public Method3D<ReturnType, DataType> cache(Map<ClassTuple, DispatchableMethodD<DataType>> cacheMap) {
		this.cache = cacheMap;
		return this;
	}
	
	public Method3D<ReturnType, DataType> unboundedCache() {
		this.cache = CacheFactory.<ClassTuple, DispatchableMethodD<DataType>>createUnboundedCache();
		return this;
	}
	
	public Method3D<ReturnType, DataType> boundedCache(long capacity) {
		this.cache = CacheFactory.<ClassTuple, DispatchableMethodD<DataType>>createBoundedCache(capacity);
		return this;
	}

}
