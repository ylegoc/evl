package evl.data;

import java.util.Map;

import evl.exceptions.BadNonVirtualParameterTypesException;
import evl.exceptions.BadNumberOfVirtualParameterTypesException;
import evl.util.CacheFactory;


public class Method3D<ReturnType, DataType> extends BaseMethod3D<ReturnType, DataType> {
	
	public Method3D<ReturnType, DataType> add(Class<?> classInstance, String name, Class<?>[] parameterTypes, Object object, DataType data) throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException {
		try {
			super.addMethod(classInstance.getMethod(name, parameterTypes), object, data);
		} catch (NoSuchMethodException | SecurityException e) {
			System.err.println("error !");
		}
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
