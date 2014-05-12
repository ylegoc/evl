package evl.base;

import java.lang.reflect.Method;
import java.util.Map;

import evl.data.BaseMethod1D;
import evl.data.DispatchableMethodD;
import evl.data.MethodComparatorD;
import evl.exceptions.BadNonVirtualParameterTypesException;
import evl.exceptions.BadNumberOfVirtualParameterTypesException;
import evl.util.CacheFactory;


public class Method1<ReturnType> extends BaseMethod1D<ReturnType, Void> {
	
	public Method1<ReturnType> add(Method method, Object object) throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException {
		super.addMethod(method, object, null);
		return this;
	}
	
	public Method1<ReturnType> addAll(Class<?> classInstance, String name, Object object) throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException {
		super.addMethodFamily(classInstance, name, object);
		return this;
	}
	
	public Method1<ReturnType> comparator(MethodComparatorD<Void> methodComparator) {
		this.methodComparator = methodComparator;
		return this;
	}
	
	public Method1<ReturnType> cache(Map<Class<?>, DispatchableMethodD<Void>> cacheMap) {
		this.cache = cacheMap;
		return this;
	}
	
	public Method1<ReturnType> unboundedCache() {
		this.cache = CacheFactory.<Class<?>, DispatchableMethodD<Void>>createUnboundedCache();
		return this;
	}
	
	public Method1<ReturnType> boundedCache(long capacity) {
		this.cache = CacheFactory.<Class<?>, DispatchableMethodD<Void>>createBoundedCache(capacity);
		return this;
	}
}
