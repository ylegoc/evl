package evl.data;

import java.lang.reflect.Method;
import java.util.AbstractMap;

import evl.exceptions.BadNonVirtualParameterTypesException;
import evl.exceptions.BadNumberOfVirtualParameterTypesException;
import evl.util.CacheFactory;


public class Method1D<ReturnType, DataType> extends BaseMethod1D<ReturnType, DataType> {
	
	private Method1D(MethodComparatorD<DataType> methodComparator, AbstractMap<Class<?>, DispatchableMethodD<DataType>> cacheMap) {
		super(methodComparator, cacheMap);
	}	
	
	public Method1D<ReturnType, DataType> add(Method method, Object object, DataType data) throws BadNumberOfVirtualParameterTypesException, BadNonVirtualParameterTypesException {
		super.addMethod(method, object, data);
		return this;
	}
	
	public static class Builder<ReturnType, DataType> {
		
		// default value for method comparator
		private MethodComparatorD<DataType> methodComparator = new AsymmetricComparatorD<DataType>();
		private AbstractMap<Class<?>, DispatchableMethodD<DataType>> cacheMap = CacheFactory.<Class<?>, DispatchableMethodD<DataType>>createUnboundedCache();
		
		public Builder<ReturnType, DataType> comparator(MethodComparatorD<DataType> methodComparator) {
			this.methodComparator = methodComparator;
			return this;
		}
		
		public Builder<ReturnType, DataType> cache(AbstractMap<Class<?>, DispatchableMethodD<DataType>> cacheMap) {
			this.cacheMap = cacheMap;
			return this;
		}
		
		public Builder<ReturnType, DataType> unboundedCache() {
			this.cacheMap = CacheFactory.<Class<?>, DispatchableMethodD<DataType>>createUnboundedCache();
			return this;
		}
		
		public Builder<ReturnType, DataType> boundedCache(long capacity) {
			this.cacheMap = CacheFactory.<Class<?>, DispatchableMethodD<DataType>>createBoundedCache(capacity);
			return this;
		}
		
		public Method1D<ReturnType, DataType> build() {
			return new Method1D<ReturnType, DataType>(methodComparator, cacheMap);
		}
	}
	
	// provided for convenience
	public static <ReturnType, DataType> Builder<ReturnType, DataType> builder() {
		return new Builder<ReturnType, DataType>();
	}

}
