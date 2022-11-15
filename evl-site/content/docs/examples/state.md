---
title: "State"
description: A changing state
weight: 5
---

The **State** pattern can be defined easily using multimethods. 
If you are not familiar with the pattern, you can have a look at the Wikipedia [state pattern](https://en.wikipedia.org/wiki/State_pattern).

This is a concrete example on how to use a multimethod for managing an inner state. It is close to [stateful class](/docs/examples/stateful-class.html).

The idea here is to define a class that will change its location state based on different criteria: person, previous location, toy, hour.
The classes used in the example are defined in the [preliminaries](/docs/examples/preliminaries.html).

### Base implementation

Here is the implementation of the *PersonPlaying* class:

```java
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
	
	protected void startMatch(Kid kid, Location previousLocation, Game game, int hour)
								throws CannotPlayException {
		throw new CannotPlayException();
	}
	
	protected void startMatch(Teen teen, Location previousLocation, Car car, int hour)
								throws CannotPlayException {
		throw new CannotPlayException();
	}
	
	protected void startMatch(Teen teen, Location previousLocation, Doll doll, int hour)
								throws CannotPlayException {
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
```

The class owns a *Person* object that is fixed and a *Location* object that can change depending on the requests.
There are two request methods: *start()* and *stop()* are the public methods which do not have the same signature.

They are implemented using two multimethods: a multimethod with dimension three for *start* and a multimethod with dimension two for *stop*.
Indeed for the *start()* method, three **virtual** parameters are used: *Person*, *Location* and *Toy*.
For the *stop()* method, two **virtual** parameters are used: *Person* and *Location*. 

The rules are defined using two sets of methods: the *startMatch()* methods and the *stopMatch()* methods. They are registered to the multimethods by passing their name to the *add()* method.


In action:

```java
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
```

This example shows how it is easy and natural to define properly the equivalent of a state pattern.
The rules are all in the same class making it easy to understand. We can even say that with the help of multimethods, states can be managed without having to conceptualize the state pattern. Moreover it clarifies the rules for changing the state and forces to think with the virtual parameters. 

### Extensibilty

Unlike the state pattern, the behaviour can easily be extended:  

* Either inherit the *PersonPlaying* class and define new rules in the new class. Then the new class must be used instead. 
* Or allow to add cases to the *start()* and *stop()* methods outside the *PersonPlaying* class. The same object can then be used.



