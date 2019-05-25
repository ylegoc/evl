/*******************************************************************************
 * Copyright 2019 The EVL authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package eu.daproject.evl.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.cayenne.util.concurrentlinkedhashmap.ConcurrentLinkedHashMap;

/**
 * Helper class to create a bounded or unbounded cache. The factory returns:
 * <ul>
 *   <li>Unbounded cache : an instance of {@link java.util.concurrent.ConcurrentHashMap}.
 *   <li>Bounded cache : an instance of {@link org.apache.cayenne.util.concurrentlinkedhashmap.ConcurrentLinkedHashMap} with maximum weighted capacity.
 * </ul>
 *
 */
public class CacheFactory {

	/**
	 * Creates an unbounded cache.
	 * @return an instance of {@link java.util.concurrent.ConcurrentHashMap}.
	 */
	public static <Key, Value> Map<Key, Value> createUnboundedCache() {
		return new ConcurrentHashMap<Key, Value>();
	}
	
	/**
	 * Creates a bounded cache.
	 * @param capacity the capacity of the cache
	 * @return an instance of {@link org.apache.cayenne.util.concurrentlinkedhashmap.ConcurrentLinkedHashMap}.
	 */
	public static <Key, Value> Map<Key, Value> createBoundedCache(int capacity) {
		return new ConcurrentLinkedHashMap.Builder<Key, Value>().maximumWeightedCapacity(capacity).build();
	}
}
