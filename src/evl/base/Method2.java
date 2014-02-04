package evl.base;

import java.lang.reflect.Method;
import java.util.AbstractMap;

import evl.data.BaseMethod2D;
import evl.data.DispatchableMethodD;
import evl.data.MethodComparatorD;
import evl.exceptions.BadNonVirtualParameterTypesException;
import evl.exceptions.BadNumberOfVirtualParameterTypesException;
import evl.util.CacheFactory;


public class Method2<ReturnType> extends BaseMethod2D<ReturnType, Void> {
	
	private Method2(MethodComparatorD<Void> methodComparator, AbstractMap<ClassTuple, DispatchableMethodD<Void>> cacheMap) {
		super(methodComparator, cacheMap);
	}
		
	public void add(Method method, Object object) throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException {
		super.add(method, object, null);
	}
	
	public static class Builder<ReturnType> {
		
		// default value for method comparator
		private MethodComparatorD<Void> methodComparator = new AsymmetricComparator();
		private AbstractMap<ClassTuple, DispatchableMethodD<Void>> cacheMap = CacheFactory.<ClassTuple, DispatchableMethodD<Void>>createUnboundedCache();
		
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
		
		public Method2<ReturnType> build() {
			return new Method2<ReturnType>(methodComparator, cacheMap);
		}
	}
	
	// provided for convenience
	public static <ReturnType> Builder<ReturnType> builder() {
		return new Builder<ReturnType>();
	}
}
