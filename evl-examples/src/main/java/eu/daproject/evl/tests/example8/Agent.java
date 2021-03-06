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
package eu.daproject.evl.tests.example8;

import eu.daproject.evl.tests.classes.A;
import eu.daproject.evl.tests.classes.B;

public class Agent {

	public int process(A a, B b) {
		return a.getA() + b.getB();
	}
	
	public int process(B b, A a) {
		return a.getA() - b.getB();
	}
}
