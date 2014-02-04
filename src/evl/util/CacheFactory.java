package evl.util;

import java.util.AbstractMap;
import java.util.concurrent.ConcurrentHashMap;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;

public class CacheFactory {

	public static <Key, Value> AbstractMap<Key, Value> createUnboundedCache() {
		return new ConcurrentHashMap<Key, Value>();
	}
	
	public static <Key, Value> AbstractMap<Key, Value> createBoundedCache(long capacity) {
		return new ConcurrentLinkedHashMap.Builder<Key, Value>().maximumWeightedCapacity(capacity).build();
	}
}
