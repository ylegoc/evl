---
title: "Visitor"
description: Visit without entering
weight: 2
---

The EVL library provides multimethods that implement multiple dispatch. The implementation of the **Visitor** pattern is immediate.
If you are not familiar with the pattern, you can have a look at the Wikipedia [visitor pattern](https://en.wikipedia.org/wiki/Visitor_pattern).

Implement the pattern requires some intrusive code and when we need to dispatch on the real type, we simply use the *instanceof* operator along with a downcast.

The classes used in the example are defined in the [preliminaries](/docs/examples/preliminaries.html).
First we define a list of *Toy* objects that we will traverse:

```java
ArrayList<Toy> toys = new ArrayList<Toy>();
toys.add(new Car("red"));
toys.add(new Doll(10));
```

### Simple dispatch

If you need to traverse the list and process the *Toy* objects, you will often write:

```java	
for (Toy toy : toys) {
	
	if (toy instanceof Car) {
		Car car = (Car)toy;
		System.out.println("Car with color " + car.getColor());
	}
	
	if (toy instanceof Doll) {
		Doll doll = (Doll)toy;
		System.out.println("Doll with height " + doll.getHeight());
	}
	
}
```

The *downcast* enables to access to the specific methods of *Car* and *Doll*. However this code is not extensible. How to do if we need to process a *Game* object? Modify the loop?
If you want to do this with standard polymorphism, you must implement the **Visitor** pattern to replace the *instanceof* operator, which is not simple and quite verbose.
Another easy way is to use a multimethod.
Here is the code:

```java
Method1<Void> play = new Method1<Void>().add(new Cases() {
			
	void match(Car car) {
		System.out.println("Car with color " + car.getColor());
	}
	
	void match(Doll doll) {
		System.out.println("Doll with height " + doll.getHeight());
	}

});

for (Toy toy : toys) {
	play.invoke(toy);
}
```

And if you need to add a new case for a *Game* object, this is naturally done:


```java
play.add(new Cases() {
			
	void match(Game game) {
		System.out.println("Game with players " + game.getPlayers());
	}
	
});
```

Adding a new case only depends on the visibility of the multimethod. If it is global, new cases can be added anywhere and the loop does not need to be extended.

### Multiple dispatch

Now we want to refine the dispatch by taking into account the status of the person playing with the toys. The status is represented by the *Person* class hierarchy including *Kid*, *Teen* and *Adult*.

We define a multimethod of dimension two, first dimension being the *Person* and second the *Toy*:

```java
Method2<Void> play = new Method2<Void>().add(new Cases() {
				
	void match(Kid kid, Car car) {
		System.out.println(kid.getAge() + " years old kid plays with car with color " + car.getColor());
	}
	
	void match(Kid kid, Doll doll) {
		System.out.println(kid.getAge() + " years old kid plays with doll with height " + doll.getHeight());
	}
	
	void match(Teen teen, Game game) {
		System.out.println(teen.getSex() + " teen plays with game with players " + game.getPlayers());
	}
	
});
```

Let's play with two persons:

```java
Person[] persons = {new Kid(5), new Teen(Teen.Sex.BOY)};

for (Person person : persons) {
	for (Toy toy : toys) {
		try {
			play.invoke(person, toy);
		}
		catch (Throwable e) {
			System.out.println("Toy cannot be played");
		}
	}
}
```

This time we needed to catch the exception when there is no compatible method. This happened for the couples (*Kid*, *Game*), (*Teen*, *Car*) and (*Teen*, *Doll*).
The exception that is thrown is an instance of *NoMatchingMethodException*. If you want to define your own exception and manage the errors yourself, this is also feasible:

We suppose that you defined the *ToyCannotBePlayedException* class.

We add a new match method that covers all the error cases:

```java
play.add(new Cases() {
				
	void match(Person person, Toy toy) throws ToyCannotBePlayedException {
		throw new ToyCannotBePlayedException();
	}
});
```

We catch it in the loop:

```java
for (Person person : persons) {
	for (Toy toy : toys) {
		try {
			play.invoke(person, toy);
		}
		catch (ToyCannotBePlayedException e) {
			System.out.println("Toy cannot be played by any person");
		}
		catch (Throwable e) {}
	}
}
```

We could have defined a match method for each error case but that is not necessary. The multimethod dispatches on the most specific couple and it is enough to define a single match method on (*Person*, *Toy*).
The couple (*Kid*, *Car*) is compatible with (*Person*, *Toy*) but there exists a perfect match method that is thus applied. However there is no perfect match for (*Kid*, *Game*) but (*Person*, *Toy*) is compatible and is thus applied.
For more details, see [best match](/docs/the-multimethods/best-match/) and [search for the best match](/docs/theory/search-best-match.html).

