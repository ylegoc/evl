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
package eu.daproject.evl.examples.example7;

import eu.daproject.evl.Method2;
import eu.daproject.evl.Priority;
import eu.daproject.evl.SymmetricComparator;
import eu.daproject.evl.examples.classes.A;
import eu.daproject.evl.examples.classes.B;
import eu.daproject.evl.examples.classes.J;
import eu.daproject.evl.examples.classes.K;

/**
 * Symmetric and asymmetric double dispatch using methods with special name "copy".
 * Example of an ambiguity and how to resolve it.
 */
public class Example7 {
	
	public static void run() throws Throwable {
		
		B b = new B(1, 4);
		K k = new K(3, 7);
		
		Copier copier = new Copier();
		
		Method2<Void> copy1 = new Method2<Void>()
				.add(copier, "copy", A.class, K.class)
				.add(copier, "copy", B.class, J.class);
		
		copy1.invoke(b, k);
		
		System.out.println(b.getB() + " == " + k.getJ());
		
		Method2<Void> copy2 = new Method2<Void>()
				.comparator(new SymmetricComparator())
				.add(copier, "copy", A.class, K.class)
				.add(copier, "copy", B.class, J.class);

		// The invocation generates an ambiguity. 
		try {
			copy2.invoke(b, k);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		// It can be resolved explicitly by defining a method for the ambiguous class tuple.
		Method2<Void> copy3 = new Method2<Void>()
				.comparator(new SymmetricComparator())
				.add(copier, "copy", A.class, K.class)
				.add(copier, "copy", B.class, J.class)
				.add(copier, "copy", B.class, K.class);

		copy3.invoke(b, k);

		System.out.println(b.getA() + " == " + k.getK());
		
		// It can be resolved implicitly by changing the priority of a method.
		Method2<Void> copy4 = new Method2<Void>()
				.comparator(new SymmetricComparator())
				.add(copier, "copy", A.class, K.class)
				.add(copier, "copy", B.class, J.class).data(Priority.valueOf(1));

		copy4.invoke(b, k);
		
		System.out.println(b.getA() + " == " + k.getK());
	}
}
