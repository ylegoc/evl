package evl.base;

import java.lang.reflect.Method;
import java.util.AbstractMap;

import evl.data.BaseMethod4D;
import evl.data.DispatchableMethodD;
import evl.data.MethodComparatorD;
import evl.exceptions.BadNonVirtualParameterTypesException;
import evl.exceptions.BadNumberOfVirtualParameterTypesException;
import evl.util.CacheFactory;


public class Method4<ReturnType> extends BaseMethod4D<ReturnType, Void> {
	
	public Method4(MethodComparatorD<Void> methodComparator, AbstractMap<ClassTuple, DispatchableMethodD<Void>> cacheMap) {
		super(methodComparator, cacheMap);
	}
		
	public Method4<ReturnType> add(Method method, Object object) throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException {
		super.addMethod(method, object, null);
		return this;
	}
	
	public Method4<ReturnType> addAll(Class<?> classInstance, String name, Object object) throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException {
		super.addMethodFamily(classInstance, name, object);
		return this;
	}
	
	public static class Builder<ReturnType> {
		
		// default value for method comparator
		private MethodComparatorD<Void> methodComparator = new AsymmetricComparator();
		private AbstractMap<ClassTuple, DispatchableMethodD<Void>> cacheMap = CacheFactory.<ClassTuple, DispatchableMethodD<Void>>createBoundedCache(10000);
		
		public Builder<ReturnType> comparator(MethodComparatorD<Void> methodComparator) {
			this.methodComparator = methodComparator;
			return this;
		}
		
		public Builder<ReturnType> cache(AbstractMap<ClassTuple, DispatchableMethodD<Void>> cacheMap) {
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
		
		public Method4<ReturnType> build() {
			return new Method4<ReturnType>(methodComparator, cacheMap);
		}
	}
	
	// provided for convenience
	public static <ReturnType> Builder<ReturnType> builder() {
		return new Builder<ReturnType>();
	}
}
