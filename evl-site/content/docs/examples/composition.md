---
title: "Composition"
description: Towards a functional approach
weight: 4
---

When a multimethod returns a polymorphic object, i.e. the return type is not the real type, the result can be injected in another multimethod.
By this way, a **chain of multiple dispatch** can be designed, that cannot be done with standard Java methods.

Here is an example of such a composition.
The classes used in the example are defined in the [preliminaries](/docs/examples/preliminaries.html).

We define a multimethod of dimension two that returns a toy depending on the person and the location:

```java
Method2<Toy> toyFinder = new Method2<Toy>().add(new Cases() {
			
	Toy match(Kid kid, Inside inside) {
		return new Doll(15);
	}
	
	Toy match(Kid kid, Outside outside) {
		return new Car("blue");
	}
	
	Toy match(Teen teen, Location location) {
		return new Game(4);
	}
});
```

We also *override* the *toString()* method of the *Toy* classes to demonstrate that it is simple:

```java
Method1<Toy> toyPrinter = new Method1<Toy>().add(new Cases() {
			
	String match(Car car) {
		return "Car with color " + car.getColor();
	}
	
	String match(Doll doll) {
		return "Doll with height " + doll.getHeight();
	}
	
	String match(Game game) {
		return "Game with players " + game.getPlayers();
	}
});
```

We can now compose these multimethods with the *PersonFinder* class defined in [stateful class](/docs/examples/stateful-class.html):

```java
PersonFinder personFinder = new PersonFinder();
		
Person[] persons = new Person[] {new Kid(5), new Teen(Sex.GIRL)};

for (Person person : persons) {
	System.out.println(person + " plays with " + 
		toyPrinter.invoke(
			toyFinder.invoke(
					person, 
					personFinder.find(person, 10))));
}
```

Composing multimethods can offer a very powerful way of designing your code.
Moreover, at each stage, each multimethod can be extended easily.
Multimethods offer new perspectives for the code design.

Multimethod composition provides a **functional approach** to polymorphism. 



