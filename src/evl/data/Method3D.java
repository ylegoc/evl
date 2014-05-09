package evl.data;

import java.lang.reflect.Method;
import java.util.Map;

import evl.exceptions.BadNonVirtualParameterTypesException;
import evl.exceptions.BadNumberOfVirtualParameterTypesException;
import evl.util.CacheFactory;


public class Method3D<ReturnType, DataType> extends BaseMethod3D<ReturnType, DataType> {
	
	private Method3D(MethodComparatorD<DataType> methodComparator, Map<ClassTuple, DispatchableMethodD<DataType>> cacheMap) {
		super(methodComparator, cacheMap);
	}	
	
	public Method3D<ReturnType, DataType> add(Method method, Object object, DataType data) throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException {
		super.addMethod(method, object, data);
		return this;
	}

	public static class Builder<ReturnType, DataType> {
		
		// default value for method comparator
		private MethodComparatorD<DataType> methodComparator = new AsymmetricComparatorD<DataType>();
		private Map<ClassTuple, DispatchableMethodD<DataType>> cacheMap = CacheFactory.<ClassTuple, DispatchableMethodD<DataType>>createBoundedCache(1000);
		
		public Builder<ReturnType, DataType> comparator(MethodComparatorD<DataType> methodComparator) {
			this.methodComparator = methodComparator;
			return this;
		}
		
		public Builder<ReturnType, DataType> cache(Map<ClassTuple, DispatchableMethodD<DataType>> cacheMap) {
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
		
		public Method3D<ReturnType, DataType> build() {
			return new Method3D<ReturnType, DataType>(methodComparator, cacheMap);
		}
	}
	
	// provided for convenience
	public static <ReturnType, DataType> Builder<ReturnType, DataType> builder() {
		return new Builder<ReturnType, DataType>();
	}
}
