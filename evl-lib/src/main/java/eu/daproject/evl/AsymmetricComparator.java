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

import eu.daproject.evl.comparators.LexicographicDistanceComparator;

/**
 * Class defining an asymmetric method comparator using the lexicographic distance comparison to compare two multimethods.
 *
 */
public class AsymmetricComparator extends MethodComparator {

	/**
	 *  Compares two {@link MethodItem} objects using the lexicographic distance comparison.
	 */
	@Override
	public int compare(MethodItem m1, MethodItem m2) {
		return super.compareWithPriority(m1, m2, LexicographicDistanceComparator.compare(m1.getDistanceTuple(), m2.getDistanceTuple()));
	}
}
