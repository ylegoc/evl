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
	
	private Method1(MethodComparatorD<Void> methodComparator, Map<Class<?>, DispatchableMethodD<Void>> cacheMap) {
		super(methodComparator, cacheMap);
	}
	
	public Method1<ReturnType> add(Method method, Object object) throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException {
		super.addMethod(method, object, null);
		return this;
	}
	
	public Method1<ReturnType> addAll(Class<?> classInstance, String name, Object object) throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException {
		super.addMethodFamily(classInstance, name, object);
		return this;
	}
	
	public static class Builder<ReturnType> {
		
		// default value for method comparator
		private MethodComparatorD<Void> methodComparator = new AsymmetricComparator();
		private Map<Class<?>, DispatchableMethodD<Void>> cacheMap = CacheFactory.<Class<?>, DispatchableMethodD<Void>>createUnboundedCache();
		
		public Builder<ReturnType> comparator(MethodComparatorD<Void> methodComparator) {
			this.methodComparator = methodComparator;
			return this;
		}
		
		public Builder<ReturnType> cache(Map<Class<?>, DispatchableMethodD<Void>> cacheMap) {
			this.cacheMap = cacheMap;
			return this;
		}
		
		public Builder<ReturnType> unboundedCache() {
			this.cacheMap = CacheFactory.<Class<?>, DispatchableMethodD<Void>>createUnboundedCache();
			return this;
		}
		
		public Builder<ReturnType> boundedCache(long capacity) {
			this.cacheMap = CacheFactory.<Class<?>, DispatchableMethodD<Void>>createBoundedCache(capacity);
			return this;
		}
		
		public Method1<ReturnType> build() {
			return new Method1<ReturnType>(methodComparator, cacheMap);
		}
	}
	
	// provided for convenience
	public static <ReturnType> Builder<ReturnType> builder() {
		return new Builder<ReturnType>();
	}
}
