---
title: "Stateful class"
description: Add a state to your class
weight: 3
---

The EVL library is very flexible and offers many ways to use the multimethods.
Here we show how to define a stateful class with protected access to a multimethod.

This is a concrete example on how to use a multimethod for managing an inner state. It is based on [protected access](/docs/more-features/protected-access.html).

The idea here is to define a person finder that will return a location based on different criteria: weather, person, hour.
The classes used in the example are defined in the [preliminaries](/docs/examples/preliminaries.html).

### Base implementation

Here is the implementation of the *PersonFinder* class:

```java
public class PersonFinder {
	
	protected Method2<Location> m = new Method2<Location>();
	protected Weather weather = new Sunny();
	
	public PersonFinder() {
		m.add(this, "findMatch");
	}
	
	public void setSunnyWeather() {
		weather = new Sunny();
	}
	
	public void setRainyWeather() {
		weather = new Rainy();
	}
	
	protected Location findMatch(Rainy weather, Kid kid, int hour) {
		return new Inside();
	}
	
	protected Location findMatch(Sunny weather, Kid kid, int hour) {
		if (hour > 14 && hour < 16) {
			return new Outside();
		}
		return new Inside();
	}
	
	protected Location findMatch(Rainy weather, Teen teen, int hour) {
		if (hour > 8 && hour < 17) {
			return new Outside();
		}
		return new Inside();
	}
	
	protected Location findMatch(Sunny weather, Teen teen, int hour) {
		if (hour > 8 && hour < 19) {
			return new Outside();
		}
		return new Inside();
	}
	
	public Location find(Person person, int hour) {
		
		try {
			return m.invoke(weather, person, hour);
		}
		catch (Throwable e) {
			return new Somewhere();
		}
	}
}
```

The class has two members:  

* The multimethod *m* of dimension two.
* The inner *weather* state.

The *weather* state can be changed publicly with the two methods *setSunnyWeather()* and *setRainyWeather()*.
The multimethod *m* is configured in the constructor by registering the match methods that are all the methods with the name "findMatch".
Then the *findMatch()* methods are defined and based on two virtual parameters - *weather* and *kid* or *teen*. They implement the find location rules.
To finish the public *find()* method is exposed which forwards the call to *m*.

In action:

```java
PersonFinder finder = new PersonFinder();
			
System.out.println("Person is " + finder.find(new Kid(6), 10));
System.out.println("Person is " + finder.find(new Teen(Sex.GIRL), 10));

finder.setRainyWeather();

System.out.println("Person is " + finder.find(new Kid(6), 18));
System.out.println("Person is " + finder.find(new Teen(Sex.GIRL), 18));
System.out.println("Person is " + finder.find(new Adult(), 18));
```

How would you have implemented such rules without multimethods? Probably by defining the weather state with an integral type (boolean, enum, integer) and using some *if* or *switch* statements.
Maybe you would have kept a class definition and use the *instanceof* operator.

What is sure is that there is no way to properly define some rules based on two class dimensions with standard "virtual" methods.

### Extension

What is interesting with multimethods is that they are very easily extended.

We want to extend the *PersonFinder* class by adding some rules concerning the *Adult* person. For that we simply define a new class that extends *PersonFinder*:

```java
public class AdultPersonFinder extends PersonFinder {

	protected Location findMatch(Weather weather, Adult adult, int hour) {
		if (hour > 8 && hour < 20) {
			return new Outside();
		}
		return new Inside();
	}
}
```

The only thing we have to do is define a new *findMatch()* method with the desired virtual parameter couples, i.e. *Weather* and *Adult* in our case.
The match method is automatically registered.

In action:

```java
PersonFinder finder = new AdultPersonFinder();
			
System.out.println("Person is " + finder.find(new Adult(), 21));
```

How would you have implemented the extension with an *if* or *switch* statements? Multimethods show that the extension is natural.

To conclude, this example is showing how to define rules based on three parameters: 

* One **virtual** state of the class
* One **virtual** parameter outside the class
* One **non-virtual** parameter

It is easy to imagine that a general stateful class would use *M* virtual states, *N* virtual parameters outside, *P* non-virtual parameters.
Of course, increase the number of virtual parameters can lead to some rules difficult to understand but the multimethods support it.
It is up to you to find good designs. 


