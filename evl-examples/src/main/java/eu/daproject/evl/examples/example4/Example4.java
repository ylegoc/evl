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
package eu.daproject.evl.examples.example4;

import eu.daproject.evl.Method1;
import eu.daproject.evl.examples.classes.A;
import eu.daproject.evl.examples.classes.B;
import eu.daproject.evl.examples.classes.C;

/**
 * Example of a stateful "agent" that counts objects.
 *
 */
public class Example4 {
	
	public static class Counter {

		public int count = 0;
		
		public void match(B obj) {
			count += obj.getA() + obj.getB();
		}

		public void match(C obj) {
			count += obj.getA() + obj.getC();
		}
	}
	
	public static void run() throws Throwable {
		
		Counter counter = new Counter();
		
		Method1<Void> method = new Method1<Void>().add(counter);
		
		A b = new B(1, 2);
		A c = new C(3, 5);
		
		method.invoke(b);
		method.invoke(c);
		
		System.out.println("Counted " + counter.count);
	}
}
