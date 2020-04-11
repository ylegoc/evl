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
package eu.daproject.evl.features.part5;

import eu.daproject.evl.Cases;

/**
 * An example of different visibilities and extensibility.
 *
 */
public class Main {
	
	public static void main(String[] args) {
		
		Print.m().add(new Cases() {
			
			String match(A a) {
				return "{ A a:" + a.a + " }";
			}
			
			String match(B b) {
				return "{ B a:" + b.a + " }";
			}
		});
		
		A a = new A();
		B b = new B(3);
		
		try {
			System.out.println(Print.m().invoke(a));
			System.out.println(Print.m().invoke(b));
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
		
		Print.m().add(new Cases() {
			
			String match(C c) throws Throwable { // Necessary
				return "{ C c:" + c.c + " a:" + Print.m().invoke(c.a) + " }";
			}
		});
		
		C c = new C(5, b);
		
		try {
			System.out.println(Print.m().invoke(c));
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
		
		ExtendedClass e = new ExtendedClass();

		System.out.println("e.foo on a: " + e.foo(a));
		System.out.println("e.foo on b: " + e.foo(b));
		
		ExtendedClass2 e2 = new ExtendedClass2();
		
		System.out.println("e2.foo on b: " + e2.foo(b));
		e2.setMultiplyOperator(); // The result changes
		System.out.println("e2.foo on b: " + e2.foo(b));
	}
}
