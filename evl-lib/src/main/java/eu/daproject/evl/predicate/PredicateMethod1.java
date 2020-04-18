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
package eu.daproject.evl.predicate;

import eu.daproject.evl.Method1;
import eu.daproject.evl.util.CacheItem;
import eu.daproject.evl.util.EmptyMap;

/**
 * Class defining a predicate multimethod of dimension 1.
 * The method comparator is set to {@link PredicateComparator} and the cache a {@link EmptyMap} instance that is always empty.
 *
 * @param <ReturnType> the return type
 */
public class PredicateMethod1<ReturnType> extends Method1<ReturnType> {
	
	/**
	 * Constructs an empty multimethod.
	 */
	public PredicateMethod1() {
		comparator(new PredicateComparator());
		cache(new EmptyMap<Class<?>, CacheItem>());
	}
	
}
