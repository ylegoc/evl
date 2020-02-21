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
package eu.daproject.evl.features.part4;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Switch;

/**
 * The simplest example with the Switch class.
 *
 */
public class Main {
	
	public static void main(String[] args) {
		
		Switch test = Switch.with(new Cases() {
				
			void match(Integer i, int v) {
				System.out.println("Integer " + (i + v));
			}
			
			void match(String s, int v) {
				System.out.println("String " + s + v);
			}
			
			void match(A a, int v) {
				System.out.println("A " + (a.a + v));
			}
		});
		
		try {
			test.invoke(Integer.valueOf(12), 3);
			test.invoke(new String("beautiful"), 11);
			test.invoke(new B(5), 4);
		
			test.invoke(Float.valueOf(13.1f), 5);
		}
		catch (Throwable e) {
			System.out.println(e.getMessage());
		}
	}
}
