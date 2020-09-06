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
package eu.daproject.evl.tests.example1;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Switch;

/**
 * The simplest example with the Switch class.
 *
 */
public class Example1 {

	public static void run() throws Throwable {
		
		Switch test = Switch.with(new Cases() {
			
			void match(Integer i) {
				System.out.println("I am the integer " + i);
			}
			
			void match(String s) {
				System.out.println("I am the string " + s);
			}
			
			void match(Collection<?> c) {
				System.out.println("I am a collection of size " + c.size());
			}
		});
		
		test.invoke(12);
		test.invoke(new String("beautiful"));
		test.invoke(Arrays.asList("what", "is", "a", "list", "?"));
	
		try {
			test.check(Float.class);
		}
		catch (Throwable e) {
			System.out.println(e.getMessage());
		}
		
		try {
			test.invoke(13.1f);
		}
		catch (Throwable e) {
			System.out.println(e.getMessage());
		}
		

	}
}
