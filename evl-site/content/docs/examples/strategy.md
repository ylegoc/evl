---
title: "Strategy"
description: Multimethod as strategy
weight: 7
---

This example is simply demonstrating how to use an EVL multimethod in a strategy pattern.
This example does not present novelties but allows to compare with the standard implementation at the Wikipedia [strategy pattern](https://en.wikipedia.org/wiki/Strategy_pattern). 

We implement the following feature: provide the duration played by a *Kid*, *Teen* or *Adult* with a *Toy* in the context of the weather.

The classes used in the example are defined in the [preliminaries](/docs/examples/preliminaries.html).

### Implementation 

The multimethod has naturally three dimensions for the *Person*, *Toy* and *Weather*. We propose some simple rules: 

```java
Method3<Integer> playDuration = new Method3<Integer>()
		.add(new Cases() {
	
	int match(Person person, Toy toy, Weather weather) throws CannotPlayException {
		throw new CannotPlayException();
	}
			
	int match(Kid kid, Car car, Sunny sunny) {
		if (kid.getAge() > 6) {
			return 40;
		}
		return 20;
	}
	
	int match(Kid kid, Car car, Rainy rainy) {
		if (kid.getAge() > 6) {
			return 50;
		}
		return 25;
	}
	
	int match(Teen teen, Game game, Weather weather) {
		return 100;
	}
	
	int match(Adult adult, Game game, Rainy rainy) {
		return 60;
	}
});
```

We covered all the cases by defining a default match method for (*Person*, *Toy*, *Weather*) that throws an exception.
Notice that an exception would be thrown by the multimethod but it is a better design to manage explicitly the case.  
For the *Kid* we return a duration that depends on his age.

To facilitate the test, we define a *test* method:

```java
void test(Method3<Integer> playDuration, Person person, Toy toy, Weather weather) throws Throwable {
	try {
		System.out.println(person + " is playing " + toy 
			+ " with " + weather + " weather during " + playDuration.invoke(person, toy, weather) + " minutes");
	}
	catch (CannotPlayException e) {
		System.out.println(person + " cannot play " + toy + " with " + weather + " weather");
	}
}
```

We can test it:

```java
Person kid = new Kid(5);
Person teen = new Teen(Sex.GIRL);
Person adult = new Adult();

Toy car = new Car("blue");
Toy game = new Game(3);

Weather weather = new Rainy();

test(playDuration, kid, car, weather);
test(playDuration, teen, game, weather);
test(playDuration, adult, game, weather);
test(playDuration, adult, car, weather);

weather = new Sunny();
		
test(playDuration, kid, car, weather);
test(playDuration, teen, game, weather);
test(playDuration, adult, game, weather);
test(playDuration, adult, car, weather);
```

The weather is changing from rainy to sunny and so the play duration.

### Extension

We realize that the behavior for the *Teen* must be refined to take into account the weather. 
The EVL multimethod allows to do it easily by simply adding some new match methods that will override the previous one:

```java
playDuration.add(new Cases() {
			
	int match(Teen teen, Game game, Sunny sunny) {
		return 90;
	}
	
	int match(Teen teen, Game game, Rainy rainy) {
		return 110;
	}
});
```

We can relaunch the test and we will obtain 110 for the *Teen*.

Notice that the match method (*Teen*, *Game*, *Weather*) is completely overriden by the new methods however it is still registered.
If a new *Cloudy* weather class would be added without any (*Teen*, *Game*, *Cloudy*) registered then it would apply. 

This example shows that the multiple dimensions of the multimethod allow to implement a strategy pattern.
This example also demonstrates that the **natural separation** between the class definitions and their behavior - *Person* classes and the match methods of the *playDuration* multimethod.   
Some more dimensions can be added if necessary for managing a more precise context e.g. having a season state.

