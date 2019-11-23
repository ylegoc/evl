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
package eu.daproject.evl.examples.example3;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method1;
import eu.daproject.evl.examples.classes.A;
import eu.daproject.evl.examples.classes.B;
import eu.daproject.evl.examples.classes.C;

/**
 * Simple example showing the minimum amount of code to be written for defining a multimethod returning a value.
 * 
 */
public class Example3 {
	
	public static void run() throws Throwable {

		Method1<Integer> method = new Method1<Integer>().add(new Cases() {

			int match(B obj) {
				return 1 + obj.getB();
			}

			int match(C obj) {
				return 2 + obj.getC();
			}
		});
		
		A b = new B(1, 2);
		A c = new C(2, -5);
		
		System.out.println(method.invoke(b));
		System.out.println(method.invoke(c));
		
		System.out.println("Method = " + method);
		
		method.printCache();
	}
}
