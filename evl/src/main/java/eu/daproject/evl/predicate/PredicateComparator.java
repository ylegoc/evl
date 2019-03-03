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

import eu.daproject.evl.MethodComparator;
import eu.daproject.evl.MethodItem;
import eu.daproject.evl.comparators.ProductDistanceComparator;

public class PredicateComparator extends MethodComparator {

	@Override
	public int compare(MethodItem m1, MethodItem m2) {
		
		// We first compare the methods.
		Predicate predicate1 = null;
		if (m1.getData() instanceof Predicate) {
			predicate1 = (Predicate)m1.getData();
			
			// Set the current args.
			predicate1.setArgs(args());
		}
		
		Predicate predicate2 = null;
		if (m2.getData() instanceof Predicate) {
			predicate2 = (Predicate)m2.getData();
			
			// Set the current args.
			predicate2.setArgs(args());
		}
		
		int comparison = 0;
		
		if (predicate1 != null) {
			// We search for the "closest" tuple, so if we want to choose m1 if it has greater boolean value i.e. m1 < m2, 
			// then we need to invert the result. 
			comparison = -predicate1.compareTo(predicate2);
		}
		
		if (comparison == 0 && predicate2 != null) {
			// Highest priority wins, so we let the result because we compare predicate2 < predicate1.
			comparison = predicate2.compareTo(predicate1);
		}
		
		if (comparison == 0) {
			// Equality, compare with symmetric comparison
			return ProductDistanceComparator.compare(m1.getDistanceTuple(), m2.getDistanceTuple());
		}
		
		return comparison;
	}

}
