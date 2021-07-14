package eu.daproject.evl.examples.state;

import eu.daproject.evl.Method2;
import eu.daproject.evl.Method3;
import eu.daproject.evl.examples.common.Car;
import eu.daproject.evl.examples.common.Doll;
import eu.daproject.evl.examples.common.Game;
import eu.daproject.evl.examples.common.Inside;
import eu.daproject.evl.examples.common.Kid;
import eu.daproject.evl.examples.common.Location;
import eu.daproject.evl.examples.common.Outside;
import eu.daproject.evl.examples.common.Person;
import eu.daproject.evl.examples.common.Teen;
import eu.daproject.evl.examples.common.Toy;

public class PersonPlaying {

	public static class CannotPlayException extends Throwable {}
	
	private Person person;
	private Location currentLocation = new Inside();
	
	protected Method3<Void> start = new Method3<Void>();
	protected Method2<Void> stop = new Method2<Void>();
	
	public PersonPlaying(Person person) {
		
		this.person = person;
		
		start.add(this, "startMatch");
		stop.add(this, "stopMatch");
	}
	
	public Location getLocation() {
		return currentLocation;
	}
	
	public Person getPerson() {
		return person;
	}
	
	protected void startMatch(Kid kid, Inside previousLocation, Car car, int hour) {
		if (hour > 14 && hour < 17) {
			currentLocation = new Outside();
		}
		else {
			currentLocation = new Inside();
		}
	}
	
	protected void startMatch(Kid kid, Outside previousLocation, Car car, int hour) {
		currentLocation = new Inside();
	}
	
	protected void startMatch(Kid kid, Location previousLocation, Doll doll, int hour) {
		currentLocation = new Inside();
	}
	
	protected void startMatch(Kid kid, Location previousLocation, Game game, int hour) throws CannotPlayException {
		throw new CannotPlayException();
	}
	
	protected void startMatch(Teen teen, Location previousLocation, Car car, int hour) throws CannotPlayException {
		throw new CannotPlayException();
	}
	
	protected void startMatch(Teen teen, Location previousLocation, Doll doll, int hour) throws CannotPlayException {
		throw new CannotPlayException();
	}
	
	protected void startMatch(Teen teen, Location previousLocation, Game game, int hour) {
		currentLocation = new Inside();
	}
	
	protected void stopMatch(Person person, Location previousLocation) {
		currentLocation = new Inside();
	}
	
	protected void stopMatch(Teen teen, Outside previousLocation) {
		currentLocation = new Inside();
	}
	
	protected void stopMatch(Teen teen, Inside previousLocation) {
		currentLocation = new Outside();
	}
	
	public void start(Toy toy, int hour) throws Throwable {
		start.invoke(person, currentLocation, toy, hour);
	}
	
	public void stop() throws Throwable {
		stop.invoke(person, currentLocation);
	}
}
