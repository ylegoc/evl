package tutorials.asteroid;

import evl.base.Method2;

/**
 * Asteroid tutorial.
 * @author yan
 *
 */
public class Main {

	public static void collide(Asteroid a, Asteroid b) {
		System.out.println("BAM! Two asteroids collide!");
	}
	
	public static void collide(Asteroid a, Spaceship b) {
		System.out.println("BOUM! Asteroid collides spaceship!");
	}
	
	public static void collide(Spaceship a, Asteroid b) {
		System.out.println("BOUM! Spaceship collides asteroid!");
	}
	
	public static void collide(Spaceship a, Spaceship b) {
		System.out.println("CRASH! Two spaceships collide!");
	}
	
	
	public static void main(String[] args) throws Exception {
	
		Method2<Void> collide = Method2.<Void>builder()
						.build()
						.addAll(Main.class, "collide", null);
		
		collide.invoke(new Asteroid(), new Asteroid());
		collide.invoke(new Asteroid(), new Spaceship());
		collide.invoke(new Spaceship(), new Asteroid());
		collide.invoke(new Spaceship(), new Spaceship());
			
	}
}
