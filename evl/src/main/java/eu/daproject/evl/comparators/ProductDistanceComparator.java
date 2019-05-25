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
package eu.daproject.evl.comparators;

/**
 * Class providing a comparator for the product distance.
 *
 */
public class ProductDistanceComparator {

	/**
	 * Compares the tuples with a product distance.
	 * @param d1 the first tuple of integers.
	 * @param d2 the second tuple of integers.
	 * @return -1 if d1 is less than d2, 1 if d1 is greater than d2, 0 otherwise.
	 */
	public static int compare(int[] d1, int[] d2) {
		
		int less = 0;
		int greater = 0;
		
		for (int i = 0; i < d1.length; i++) {
			if (d1[i] < d2[i]) {
				less++;
			} else if (d1[i] > d2[i]) {
				greater++;
			}
		}
		
		if (less > 0 && greater == 0) {
			return -1;
		}
		
		if (greater > 0 && less == 0) {
			return 1;
		}
		
		// Equality.
		return 0;
	}
}
