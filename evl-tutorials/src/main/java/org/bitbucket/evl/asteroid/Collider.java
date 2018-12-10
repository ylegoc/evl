package org.bitbucket.evl.asteroid;

import org.bitbucket.evl.Cases;
import org.bitbucket.evl.Method2;

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
			
			public void match(Asteroid a, Asteroid b) {
				System.out.println("BAM! Two asteroids collide!");
			}
			
			public void match(Asteroid a, Spaceship b) {
				System.out.println("BOUM! Asteroid collides spaceship!");
			}
			
			public void match(Spaceship a, Asteroid b) {
				System.out.println("BOUM! Spaceship collides asteroid!");
			}
			
			public void match(Spaceship a, Spaceship b) {
				System.out.println("CRASH! Two spaceships collide!");
			}
		});
		
		collide.invoke(new Asteroid(), new Asteroid());
		collide.invoke(new Asteroid(), new Spaceship());
		collide.invoke(new Spaceship(), new Asteroid());
		collide.invoke(new Spaceship(), new Spaceship());
	}
}
