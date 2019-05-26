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

import eu.daproject.evl.asteroid.Collider;
import eu.daproject.evl.example.Example1;
import eu.daproject.evl.example.Example2;
import eu.daproject.evl.tutorial1.Tutorial1;
import eu.daproject.evl.tutorial2.Tutorial2;
import eu.daproject.evl.tutorial3.Tutorial3;
import eu.daproject.evl.tutorial4.Tutorial4;
import eu.daproject.evl.tutorial5.Tutorial5;
import eu.daproject.evl.tutorial6.Tutorial6;
import eu.daproject.evl.tutorial7.Tutorial7;
import eu.daproject.evl.tutorial8.Tutorial8;
import eu.daproject.evl.tutorial9.Tutorial9;

public class Tutorials {

	public static void main(String[] args) throws Throwable {
	
		System.out.println("EVL Tutorials");
		
		System.out.println("");
		System.out.println("*** Examples ***");
		Example1.run();
		System.out.println("");
		Example2.run();
		System.out.println("");
		
		System.out.println("");
		System.out.println("*** Asteroid ***");
		Collider.run();
		System.out.println("");
		
		System.out.println("");
		System.out.println("*** Tutorial 1 ***");
		Tutorial1.run();
		System.out.println("");
		
		System.out.println("");
		System.out.println("*** Tutorial 2 ***");
		Tutorial2.run();
		System.out.println("");
		
		System.out.println("");
		System.out.println("*** Tutorial 3 ***");
		Tutorial3.run();
		System.out.println("");
		
		System.out.println("");
		System.out.println("*** Tutorial 4 ***");
		Tutorial4.run();
		System.out.println("");
		
		System.out.println("");
		System.out.println("*** Tutorial 5 ***");
		Tutorial5.run();
		System.out.println("");
		
		System.out.println("");
		System.out.println("*** Tutorial 6 ***");
		Tutorial6.run();
		System.out.println("");
		
		System.out.println("");
		System.out.println("*** Tutorial 7 ***");
		Tutorial7.run();
		System.out.println("");
		
		System.out.println("");
		System.out.println("*** Tutorial 8 ***");
		Tutorial8.run();
		System.out.println("");
		
		System.out.println("");
		System.out.println("*** Tutorial 9 ***");
		Tutorial9.run();
		System.out.println("");
	}
}
