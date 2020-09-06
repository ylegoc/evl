package eu.daproject.evl.examples.statefulclass;

import eu.daproject.evl.Method2;
import eu.daproject.evl.examples.common.Kid;
import eu.daproject.evl.examples.common.Person;
import eu.daproject.evl.examples.common.Rainy;
import eu.daproject.evl.examples.common.Sunny;
import eu.daproject.evl.examples.common.Teen;
import eu.daproject.evl.examples.common.Weather;

public class PersonFinder {

	public enum Location {OUTSIDE, INSIDE, UNKNOWN};
	
	protected Method2<Location> m = new Method2<Location>();
	protected Weather weather = new Sunny();
	
	public PersonFinder() {
		
		m.access(PersonFinder.class);
		m.add(this, "findMatch");
	}
	
	public void setSunnyWeather() {
		weather = new Sunny();
	}
	
	public void setRainyWeather() {
		weather = new Rainy();
	}
	
	protected Location findMatch(Rainy weather, Kid kid, int hour) {
		return Location.INSIDE;
	}
	
	protected Location findMatch(Sunny weather, Kid kid, int hour) {
		if (hour > 14 && hour < 16) {
			return Location.OUTSIDE;
		}
		return Location.INSIDE;
	}
	
	protected Location findMatch(Rainy weather, Teen teen, int hour) {
		if (hour > 8 && hour < 17) {
			return Location.OUTSIDE;
		}
		return Location.INSIDE;
	}
	
	protected Location findMatch(Sunny weather, Teen teen, int hour) {
		if (hour > 8 && hour < 19) {
			return Location.OUTSIDE;
		}
		return Location.INSIDE;
	}
	
	public Location find(Person person, int hour) {
		
		try {
			return m.invoke(weather, person, hour);
		}
		catch (Throwable e) {
			return Location.UNKNOWN;
		}
	}
}
