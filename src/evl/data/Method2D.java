package evl.data;

import java.lang.reflect.Method;
import java.util.AbstractMap;

import evl.exceptions.BadNonVirtualParameterTypesException;
import evl.exceptions.BadNumberOfVirtualParameterTypesException;
import evl.util.CacheFactory;


public class Method2D<ReturnType, DataType> extends BaseMethod2D<ReturnType, DataType> {
	
	private Method2D(MethodComparatorD<DataType> methodComparator, AbstractMap<ClassTuple, DispatchableMethodD<DataType>> cacheMap) {
		super(methodComparator, cacheMap);
	}	
	
	public void add(Method method, Object object, DataType data) throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException {
		super.add(method, object, data);
	}
	
	public static class Builder<ReturnType, DataType> {
		
		// default value for method comparator
		private MethodComparatorD<DataType> methodComparator = new AsymmetricComparatorD<DataType>();
		private AbstractMap<ClassTuple, DispatchableMethodD<DataType>> cacheMap = CacheFactory.<ClassTuple, DispatchableMethodD<DataType>>createUnboundedCache();
		
		public Builder<ReturnType, DataType> comparator(MethodComparatorD<DataType> methodComparator) {
			this.methodComparator = methodComparator;
			return this;
		}
		
		public Builder<ReturnType, DataType> cache(AbstractMap<ClassTuple, DispatchableMethodD<DataType>> cacheMap) {
			this.cacheMap = cacheMap;
			return this;
		}
		
		public Builder<ReturnType, DataType> unboundedCache() {
			this.cacheMap = CacheFactory.<ClassTuple, DispatchableMethodD<DataType>>createUnboundedCache();
			return this;
		}
		
		public Builder<ReturnType, DataType> boundedCache(long capacity) {
			this.cacheMap = CacheFactory.<ClassTuple, DispatchableMethodD<DataType>>createBoundedCache(capacity);
			return this;
		}
		
		public Method2D<ReturnType, DataType> build() {
			return new Method2D<ReturnType, DataType>(methodComparator, cacheMap);
		}
	}
	
	// provided for convenience
	public static <ReturnType, DataType> Builder<ReturnType, DataType> builder() {
		return new Builder<ReturnType, DataType>();
	}
}
