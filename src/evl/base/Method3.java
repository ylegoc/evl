package evl.base;

import java.util.Map;

import evl.data.BaseMethod3D;
import evl.data.DispatchableMethodD;
import evl.data.MethodComparatorD;
import evl.exceptions.BadNonVirtualParameterTypesException;
import evl.exceptions.BadNumberOfVirtualParameterTypesException;
import evl.util.CacheFactory;


public class Method3<ReturnType> extends BaseMethod3D<ReturnType, Void> {
	
	public Method3<ReturnType> add(Class<?> classInstance, String name, Class<?>[] parameterTypes, Object object) throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException {
		try {
			super.addMethod(classInstance.getMethod(name, parameterTypes), object, null);
		} catch (NoSuchMethodException | SecurityException e) {
			System.err.println("error !");
		}
		return this;
	}
	
	public Method3<ReturnType> addAll(Class<?> classInstance, String name, Object object) throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException {
		super.addMethodFamily(classInstance, name, object);
		return this;
	}
	
	public Method3<ReturnType> comparator(MethodComparatorD<Void> methodComparator) {
		this.methodComparator = methodComparator;
		return this;
	}
	
	public Method3<ReturnType> cache(Map<ClassTuple, DispatchableMethodD<Void>> cacheMap) {
		this.cache = cacheMap;
		return this;
	}
	
	public Method3<ReturnType> unboundedCache() {
		this.cache = CacheFactory.<ClassTuple, DispatchableMethodD<Void>>createUnboundedCache();
		return this;
	}
	
	public Method3<ReturnType> boundedCache(long capacity) {
		this.cache = CacheFactory.<ClassTuple, DispatchableMethodD<Void>>createBoundedCache(capacity);
		return this;
	}

}
