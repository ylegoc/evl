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
