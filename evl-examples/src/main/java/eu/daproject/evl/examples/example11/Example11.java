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
package eu.daproject.evl.examples.example11;

import eu.daproject.evl.examples.classes.A;
import eu.daproject.evl.examples.classes.B;
import eu.daproject.evl.exception.InvocationException;
import eu.daproject.evl.predicate.Predicate;
import eu.daproject.evl.predicate.PredicateMethod1;

/**
 * Example with predicate dispatch in data.
 * 
 */
public class Example11 {
	
	public static class Foo {

		public int process(A obj, int x) {
			return 1;
		}
		
		public boolean test(A obj, int x) {
			return (x < 0);
		}

		public int process(B obj, int x) {
			return 2;
		}
		
		public boolean test(B obj, int x) {
			return (x >= 10 && x < 20);
		}
		
		public int process2(B obj, int x) {
			return 3;
		}
		
		public boolean test2(B obj, int x) {
			return (x >= 20);
		}
	}
	
	public static void run() throws Throwable {
		
		A b = new B(2, -5);
		
		Foo foo = new Foo();
		
		PredicateMethod1<Integer> predicateMethod = new PredicateMethod1<Integer>();
						
		predicateMethod.add(foo, "process", A.class, int.class).data(new Predicate(foo, "test", A.class, int.class));
		predicateMethod.add(foo, "process", B.class, int.class).data(new Predicate(foo, "test", B.class, int.class));
		predicateMethod.add(foo, "process2", B.class, int.class).data(new Predicate(foo, "test2", B.class, int.class));
		
		System.out.println(predicateMethod.invoke(b, -1));
		
		try {
			System.out.println(predicateMethod.invoke(b, 1));
		}
		catch (InvocationException e) {
			System.out.println("No function for x = 1");
		}
		
		System.out.println(predicateMethod.invoke(b, 11));
		System.out.println(predicateMethod.invoke(b, 21));
		
		System.out.println("Predicate method = " + predicateMethod);
	}
}
