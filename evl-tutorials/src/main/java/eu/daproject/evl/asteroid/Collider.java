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
package eu.daproject.evl.asteroid;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method2;

/**
 * The classic Asteroid double dispatch example.
 *
 */
public class Collider {

	public static class Asteroid {
	}
	
	public static class Spaceship {
	}
	
	public static void run() throws Throwable {
	
		Method2<Void> collide = new Method2<Void>().add(new Cases() {
			
			void match(Asteroid a, Asteroid b) {
				System.out.println("BAM! Two asteroids collide!");
			}
			
			void match(Asteroid a, Spaceship b) {
				System.out.println("BOUM! Asteroid collides spaceship!");
			}
			
			void match(Spaceship a, Asteroid b) {
				System.out.println("BOUM! Spaceship collides asteroid!");
			}
			
			void match(Spaceship a, Spaceship b) {
				System.out.println("CRASH! Two spaceships collide!");
			}
		});
		
		collide.invoke(new Asteroid(), new Asteroid());
		collide.invoke(new Asteroid(), new Spaceship());
		collide.invoke(new Spaceship(), new Asteroid());
		collide.invoke(new Spaceship(), new Spaceship());
	}
}
