---
title: "Factory"
description: Create the elements
weight: 8
---

This example is simply demonstrating how to use an EVL multimethod in a factory pattern.
This example does not present novelties but allows to compare with the standard implementations. 

We implement the following feature: provide a factory for *Toy* and *Container* in the context of a *Person* and a *Location*.
For example, what is the *Toy* created for a *Kid* when location is *Inside*.

The classes used in the example are defined in the [preliminaries](/docs/examples/preliminaries.html).

### Implementation 

We define the *Factory* class, that has two multimethods of two dimensions for the *Person* and *Location* and two main methods *createToy* and *createContainer*:

```java
public class Factory {

	public static class CannotCreateException extends Throwable {}
	
	protected Method2<Toy> toyFactory = new Method2<Toy>();
	protected Method2<Container> containerFactory = new Method2<Container>();
	
	public Factory() {
		
		toyFactory.add(new Cases() {
			
			Toy match(Kid kid, Inside inside) {
				return new Doll(20);
			}
			
			Toy match(Kid kid, Outside outside) {
				return new Car("red");
			}
			
			Toy match(Teen teen, Inside inside) {
				return new Game(3);
			}
			
			Toy match(Teen teen, Outside outside) {
				return new Game(6);
			}
			
			Toy match(Adult adult, Inside inside) {
				return new Game(2);
			}
		});
		
		containerFactory.add(new Cases() {
			
			Container match(Kid kid, Inside inside) {
				return new Tallboy("wood");
			}
			
			Container match(Kid kid, Outside outside) {
				return new Box(20);
			}
			
			Container match(Teen teen, Inside inside) {
				return new Tallboy("plastic");
			}
			
			Container match(Teen teen, Outside outside) {
				return new GoldenBox(30);
			}
			
			Container match(Adult adult, Inside inside) {
				return new Room("white");
			}
		});
	}
	
	public void addToyCases(Cases cases) {
		toyFactory.add(cases);
	}
	
	public void addContainerCases(Cases cases) {
		containerFactory.add(cases);
	}
	
	public Toy createToy(Person person, Location location) throws CannotCreateException {
		try {
			return toyFactory.invoke(person, location);
		}
		catch (Throwable e) {
			throw new CannotCreateException();
		}
	}
	
	public Container createContainer(Person person, Location location) throws CannotCreateException {
		try {
			return containerFactory.invoke(person, location);
		}
		catch (Throwable e) {
			throw new CannotCreateException();
		}
	}
}
```

We add the match methods in the constructor and the methods *createToy* and *createContainer* simply forward the call to the concerned multimethod. 

To facilitate the test, we define the *testToy* and *testContainer* methods:

```java
void testToy(Factory factory, Person person, Location location) throws Throwable {
	System.out.println("Toy created for " + person + " is " + factory.createToy(person, location));
}

void testContainer(Factory factory, Person person, Location location) throws Throwable {
	System.out.println("Container created for " + person + " is " + factory.createContainer(person, location));
}
```

We can test it:

```java
Factory factory = new Factory();
		
Person kid = new Kid(5);
Person teen = new Teen(Sex.GIRL);
Person adult = new Adult();

testToy(factory, kid, new Inside());
testToy(factory, teen, new Outside());

testContainer(factory, teen, new Outside());
testContainer(factory, adult, new Inside());
```

No problem, the good objects are created without any exception.

### Extension

The EVL multimethods are easily extensible and we realize that we need to add a new case in the creation of a *Toy* for an *Adult* in the location *Outside*.
The *Factory* class is already ready for that thanks to the *addToyCases* method:

```java
factory.addToyCases(new Cases() {
			
	Toy match(Adult adult, Outside outside) {
		return new Game(4);
	}
});
```

No need to define a subclass to *Factory*, adding new cases is just enough.

This example is once again the demonstration that a multimethod with more than one dimension can be very useful to define an object factory.
The common implementations are only limited to one dimension because *virtual* methods in Java are one-dimensional.

