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
		
		// Compare the distance tuple of each multimethod.
		int comparison = LexicographicDistanceComparator.compare(m1.getDistanceTuple(), m2.getDistanceTuple());
		if (comparison == 0) {
			Priority priority1 = null;
			if (m1.getData() == null) {
				priority1 = Priority.valueOf(0);
			}
			else if (m1.getData() instanceof Priority) {
				priority1 = (Priority)m1.getData();
			}
			
			Priority priority2 = null;
			if (m2.getData() == null) {
				priority2 = Priority.valueOf(0);
			}
			else if (m2.getData() instanceof Priority) {
				priority2 = (Priority)m2.getData();
			}
			
			if (priority1 != null) {
				// We search for the "closest" tuple, so if we want to choose m1 if it has greater priority i.e. m1 < m2, 
				// then we need to invert the result. 
				return -priority1.compareTo(priority2);
			}
			
			if (priority2 != null) {
				// Highest priority wins, so we let the result because we compare prority2 < priority1.
				return priority2.compareTo(priority1);
			}
		}
		
		return comparison;
	}
}
