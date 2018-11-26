package org.bitbucket.evl.asteroid;

import org.bitbucket.evl.Method2;

/**
 * Asteroid tutorial.
 * @author yan
 *
 */
public class Collider {

	public static void match(Asteroid a, Asteroid b) {
		System.out.println("BAM! Two asteroids collide!");
	}
	
	public static void match(Asteroid a, Spaceship b) {
		System.out.println("BOUM! Asteroid collides spaceship!");
	}
	
	public static void match(Spaceship a, Asteroid b) {
		System.out.println("BOUM! Spaceship collides asteroid!");
	}
	
	public static void match(Spaceship a, Spaceship b) {
		System.out.println("CRASH! Two spaceships collide!");
	}
	
	
	public static void main(String[] args) throws Throwable {
	
		Method2<Void> collide = new Method2<Void>().add(new Collider());
		
		collide.invoke(new Asteroid(), new Asteroid());
		collide.invoke(new Asteroid(), new Spaceship());
		collide.invoke(new Spaceship(), new Asteroid());
		collide.invoke(new Spaceship(), new Spaceship());
	}
}
