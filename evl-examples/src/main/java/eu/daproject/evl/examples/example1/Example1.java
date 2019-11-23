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
package eu.daproject.evl.examples.example1;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Switch;

/**
 * The simplest example with the Switch class.
 *
 */
public class Example1 {

	static class A {
		public int a = 2;
	}
	
	static class B extends A {
		
		public B(int a) {
			this.a = a;
		}
	}
	
	public static void run() throws Throwable {
		
		Switch test = Switch.with(new Cases() {
				
			void match(Integer i) {
				System.out.println("I am the integer " + i);
			}
			
			void match(String s) {
				System.out.println("I am the string " + s);
			}
			
			void match(A a) {
				System.out.println("I am an A with a = " + a.a);
			}
		});
		
		test.invoke(new Integer(12));
		test.invoke(new String("beautiful"));
		test.invoke(new B(5));
		
		try {
			test.invoke(new Float(13.1f));
		}
		catch (Throwable e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
