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
package eu.daproject.evl.examples.example2;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method2;

/**
 * A simple example of multimethod of dimension 2.
 *
 */
public class Example2 {

	static class A {
		public int a = 2;
	}
	
	static class B extends A {
		
		public B(int a) {
			this.a = a;
		}
	}
	
	public static void run() throws Throwable {
		
		Method2<Void> test = new Method2<Void>().add(new Cases() {
				
			void match(Integer i, Integer j) {
				System.out.println("We are the integers " + i + ", " + j);
			}
			
			void match(String s, String t) {
				System.out.println("We are the strings " + s + ", " + t);
			}
			
			void match(A a, A b) {
				System.out.println("We are A " + a.a + ", " + b.a);
			}
		});
		
		test.invoke(Integer.valueOf(12), Integer.valueOf(-25));
		test.invoke(new String("beautiful"), new String("day"));
		test.invoke(new B(5), new B(6));
		
		try {
			test.invoke(new String("string"), Integer.valueOf(2));
		}
		catch (Throwable e) {
			System.out.println(e.getMessage());
		}
	}
}
