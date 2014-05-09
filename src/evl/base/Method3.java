package evl.base;

import java.lang.reflect.Method;
import java.util.Map;

import evl.data.BaseMethod3D;
import evl.data.DispatchableMethodD;
import evl.data.MethodComparatorD;
import evl.exceptions.BadNonVirtualParameterTypesException;
import evl.exceptions.BadNumberOfVirtualParameterTypesException;
import evl.util.CacheFactory;


public class Method3<ReturnType> extends BaseMethod3D<ReturnType, Void> {
	
	public Method3(MethodComparatorD<Void> methodComparator, Map<ClassTuple, DispatchableMethodD<Void>> cacheMap) {
		super(methodComparator, cacheMap);
	}
		
	public Method3<ReturnType> add(Method method, Object object) throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException {
		super.addMethod(method, object, null);
		return this;
	}
	
	public Method3<ReturnType> addAll(Class<?> classInstance, String name, Object object) throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException {
		super.addMethodFamily(classInstance, name, object);
		return this;
	}
	
	public static class Builder<ReturnType> {
		
		// default value for method comparator
		private MethodComparatorD<Void> methodComparator = new AsymmetricComparator();
		private Map<ClassTuple, DispatchableMethodD<Void>> cacheMap = CacheFactory.<ClassTuple, DispatchableMethodD<Void>>createBoundedCache(1000);
		
		public Builder<ReturnType> comparator(MethodComparatorD<Void> methodComparator) {
			this.methodComparator = methodComparator;
			return this;
		}
		
		public Builder<ReturnType> cache(Map<ClassTuple, DispatchableMethodD<Void>> cacheMap) {
			this.cacheMap = cacheMap;
			return this;
		}
		
		public Builder<ReturnType> unboundedCache() {
			this.cacheMap = CacheFactory.<ClassTuple, DispatchableMethodD<Void>>createUnboundedCache();
			return this;
		}
		
		public Builder<ReturnType> boundedCache(long capacity) {
			this.cacheMap = CacheFactory.<ClassTuple, DispatchableMethodD<Void>>createBoundedCache(capacity);
			return this;
		}
		
		public Method3<ReturnType> build() {
			return new Method3<ReturnType>(methodComparator, cacheMap);
		}
	}
	
	// provided for convenience
	public static <ReturnType> Builder<ReturnType> builder() {
		return new Builder<ReturnType>();
	}
}
