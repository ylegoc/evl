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

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Empty map class used by the {@link eu.daproject.evl.predicate.PredicateMethod1} and other predicate multimethods that cannot use a cache.
 * The methods do nothing.
 *
 * @param <V> the value type.
 * @param <K> the key type.
 */
public class EmptyMap<V, K> implements Map<V, K> {

	/**
	 * Does nothing.
	 */
	@Override
	public void clear() {
	}

	/**
	 * Returns false.
	 */
	@Override
	public boolean containsKey(Object key) {
		return false;
	}

	/**
	 * Returns false.
	 */
	@Override
	public boolean containsValue(Object value) {
		return false;
	}

	/**
	 * Returns null.
	 */
	@Override
	public Set<java.util.Map.Entry<V, K>> entrySet() {
		return null;
	}

	/**
	 * Returns null.
	 */
	@Override
	public K get(Object key) {
		return null;
	}

	/**
	 * Returns false.
	 */
	@Override
	public boolean isEmpty() {
		return false;
	}

	/**
	 * Returns null.
	 */
	@Override
	public Set<V> keySet() {
		return null;
	}

	/**
	 * Returns null.
	 */
	@Override
	public K put(V key, K value) {
		return null;
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void putAll(Map<? extends V, ? extends K> m) {
	}

	/**
	 * Returns null.
	 */
	@Override
	public K remove(Object key) {
		return null;
	}

	/**
	 * Returns 0.
	 */
	@Override
	public int size() {
		return 0;
	}

	/**
	 * Returns null.
	 */
	@Override
	public Collection<K> values() {
		return null;
	}

}
