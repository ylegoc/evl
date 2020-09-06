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
package eu.daproject.evl;

import eu.daproject.evl.tests.example1.Example1;
import eu.daproject.evl.tests.example10.Example10;
import eu.daproject.evl.tests.example11.Example11;
import eu.daproject.evl.tests.example2.Example2;
import eu.daproject.evl.tests.example3.Example3;
import eu.daproject.evl.tests.example4.Example4;
import eu.daproject.evl.tests.example5.Example5;
import eu.daproject.evl.tests.example6.Example6;
import eu.daproject.evl.tests.example7.Example7;
import eu.daproject.evl.tests.example8.Example8;
import eu.daproject.evl.tests.example9.Example9;
import eu.daproject.evl.whatisevl.Collider;

public class Examples {

	public static void main(String[] args) throws Throwable {
	
		System.out.println("EVL Tutorials");
		
		System.out.println("");
		System.out.println("*** Asteroid ***");
		Collider.run();
		System.out.println("");
		
		System.out.println("");
		System.out.println("*** Example 1 ***");
		Example1.run();
		System.out.println("");

		System.out.println("");
		System.out.println("*** Example 2 ***");
		Example2.run();
		System.out.println("");

		System.out.println("");
		System.out.println("*** Example 3 ***");
		Example3.run();
		System.out.println("");
		
		System.out.println("");
		System.out.println("*** Example 4 ***");
		Example4.run();
		System.out.println("");
		
		System.out.println("");
		System.out.println("*** Example 5 ***");
		Example5.run();
		System.out.println("");
		
		System.out.println("");
		System.out.println("*** Example 6 ***");
		Example6.run();
		System.out.println("");
		
		System.out.println("");
		System.out.println("*** Example 7 ***");
		Example7.run();
		System.out.println("");
		
		System.out.println("");
		System.out.println("*** Example 8 ***");
		Example8.run();
		System.out.println("");
		
		System.out.println("");
		System.out.println("*** Example 9 ***");
		Example9.run();
		System.out.println("");
		
		System.out.println("");
		System.out.println("*** Example 10 ***");
		Example10.run();
		System.out.println("");
		
		System.out.println("");
		System.out.println("*** Example 11 ***");
		Example11.run();
		System.out.println("");
	}
}
