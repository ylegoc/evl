package org.bitbucket.evl.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.cayenne.util.concurrentlinkedhashmap.ConcurrentLinkedHashMap;

public class CacheFactory {

	public static <Key, Value> Map<Key, Value> createUnboundedCache() {
		return new ConcurrentHashMap<Key, Value>();
	}
	
	public static <Key, Value> Map<Key, Value> createBoundedCache(int capacity) {
		return new ConcurrentLinkedHashMap.Builder<Key, Value>().maximumWeightedCapacity(capacity).build();
	}
}
