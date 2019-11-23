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
package eu.daproject.evl.examples.example6;

import eu.daproject.evl.examples.classes.A;
import eu.daproject.evl.examples.classes.B;
import eu.daproject.evl.examples.classes.C;

/**
 * "Open-method" example. A singleton multimethod can be configured anywhere.
 *
 */
public class Example6 {
	
	public static class Foo1 {

		public int match(B obj) {
			return 1 + obj.getB();
		}
	}
	
	public static class Foo2 {

		public int match(C obj) {
			return 2 + obj.getC();
		}
	}
	
	public static void run() throws Throwable {
		
		A b = new B(1, 2);
		A c = new C(2, -5);
		
		Foo1 foo1 = new Foo1();
		Process.instance().add(foo1);
		
		Foo2 foo2 = new Foo2();
		Process.instance().add(foo2);
		
		System.out.println(Process.instance().invoke(b));
		System.out.println(Process.instance().invoke(c));
	}
}
