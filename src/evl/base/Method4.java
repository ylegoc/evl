package evl.base;

import java.lang.reflect.Method;
import java.util.AbstractMap;

import evl.base.Method3.Builder;
import evl.data.DispatchableMethodD;
import evl.data.BaseMethod4D;
import evl.data.MethodComparatorD;
import evl.data.BaseMethod3D.ClassTuple;
import evl.exceptions.BadNonVirtualParameterTypesException;
import evl.exceptions.BadNumberOfVirtualParameterTypesException;
import evl.util.CacheFactory;


public class Method4<ReturnType> extends BaseMethod4D<ReturnType, Void> {
	
	public Method4(MethodComparatorD<Void> methodComparator, AbstractMap<ClassTuple, DispatchableMethodD<Void>> cacheMap) {
		super(methodComparator, cacheMap);
	}
		
	public void add(Method method, Object object) throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException {
		super.add(method, object, null);
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
