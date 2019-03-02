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
package eu.daproject.evl;

/**
 * Class providing a base for the method comparators. The arguments passed to the calling invoke are set at thread-local.
 * They can be used for predicate based dispatch where there is no cache.
 */
public abstract class MatchMethodComparator {

	private ThreadLocal<Object[]> threadLocalArgs = new ThreadLocal<Object[]>();
	
	public Object[] args() {
		return threadLocalArgs.get();
	}

	void setArgs(Object[] args) {
		threadLocalArgs.set(args);
	}
	
	public abstract int compare(MatchMethodItem m1, MatchMethodItem m2);
	
}
