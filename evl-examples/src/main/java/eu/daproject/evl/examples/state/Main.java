package eu.daproject.evl.examples.state;

import eu.daproject.evl.examples.common.Car;
import eu.daproject.evl.examples.common.Kid;

public class Main {
	
	public static void main(String[] args) {
		
		PersonPlaying playing = new PersonPlaying(new Kid(7));
		
		System.out.println(playing.getPerson() + " is " + playing.getLocation());
		
		try {
			playing.start(new Car("blue"), 10);
			System.out.println(playing.getPerson() + " is playing " + playing.getLocation());
			
			playing.start(new Car("blue"), 15);
			System.out.println(playing.getPerson() + " is playing " + playing.getLocation());
			
			playing.stop();
			System.out.println(playing.getPerson() + " has stopped playing and is now " + playing.getLocation());
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
