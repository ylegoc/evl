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
package eu.daproject.evl.tests.example8;

import eu.daproject.evl.Method2;
import eu.daproject.evl.SymmetricComparator;
import eu.daproject.evl.exception.InvocationException;
import eu.daproject.evl.tests.classes.A;
import eu.daproject.evl.tests.classes.B;

/**
 * Example on how to extend an "agent" class.
 *
 */
public class Example8 {
	
	public static void run() throws Throwable {
		
		A b1 = new B(1, 2);
		A b2 = new B(2, -5);
		
		Agent agent = new Agent();
		
		Method2<Integer> method = new Method2<Integer>()
						.comparator(new SymmetricComparator())
						.add(agent, "process");

		try {
			System.out.println(method.invoke(b1, b2));
		}
		catch (InvocationException e) {
			System.out.println(e.getMessage());
		}
		
		ExtendedAgent agent2 = new ExtendedAgent();
		
		method = new Method2<Integer>()
				.comparator(new SymmetricComparator())
				.add(agent2, "process");

		System.out.println(method.invoke(b1, b2));
	}
}
