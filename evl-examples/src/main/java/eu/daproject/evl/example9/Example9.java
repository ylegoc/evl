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
package eu.daproject.evl.example9;

import eu.daproject.evl.Cases;
import eu.daproject.evl.MethodComparator;
import eu.daproject.evl.MethodItem;
import eu.daproject.evl.Method2;
import eu.daproject.evl.classes.A;
import eu.daproject.evl.classes.B;

/**
 * Example of a custom method comparator.
 *
 */
public class Example9 {
	
	public static class ClassNameComparator extends MethodComparator {

		@Override
		public int compare(MethodItem m1, MethodItem m2) {
			
			int length = m1.getClassTuple().get().length;
			String name1 = "";
			String name2 = "";
			
			for (int i = 0; i < length; ++i) {
				name1 += m1.getClassTuple().get()[i].getName() + ";";
				name2 += m2.getClassTuple().get()[i].getName() + ";";
			}
			
			int result = name1.compareTo(name2);
			
			return result;
		}

	}
	
	public static void run() throws Throwable {
		
		A b1 = new B(1, 2);
		A b2 = new B(2, -5);
		
		Method2<Integer> method = new Method2<Integer>()
				.comparator(new ClassNameComparator())
				.add(new Cases() {
					int match(A a, B b) {
						return a.getA() + b.getB();
					}
				
					int match(B b, A a) {
						return a.getA() - b.getB();
					}
				});

		System.out.println(method.invoke(b1, b2));
	}
}
