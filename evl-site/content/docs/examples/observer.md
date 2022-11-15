---
title: "Observer"
description: Observe the elements
weight: 9
---

This example is simply demonstrating how to implement the observer pattern by using an EVL multimethod.
This example does not present novelties but allows to compare with the standard implementations. 

We adapt the example shown in Wikipedia [observer pattern](https://en.wikipedia.org/wiki/Observer_pattern).

We use instead the classes defined in the [preliminaries](/docs/examples/preliminaries.html).
We replace the *Observer* class by a *Container* class and the *Event* class by a *Toy* class.
The idea is to update the *Container* objects when they receive a *Toy*. Every combination is not possible mainly due to the place. 

### Implementation 

We define the *ToySource* class equivalent to the *EventSource* class, that has one multimethod of dimension two:

```java
public class ToySource {

	private List<Container> containers = new ArrayList<>();
	private Method2<Boolean> update = new Method2<Boolean>();
	
	void addContainer(Container container) {
		containers.add(container);
	}
	
	void addContainerCases(Cases cases) {
		update.add(cases);
	}
	  
	void update(Toy toy) {
		
		containers.forEach(container -> {
			try {
				if (update.invoke(container, toy)) {
					System.out.println(container + " added " + toy);
				}
			}
			catch (Throwable e) {
				System.err.println("Cannot update " + container +  " with " + toy);
			}	
		});
	}
}
```

The class has a list of observers - the containers - and the multimethod is invoked for each observer in the *update* method.
The main difference with the standard *EventSource* implementation is that the *update* method of the observers is **not attached** with the observer by implementing the abstract method *update* but separated and **added as a** ***match*** method.

Then we can instantiate and use it:

```java
ToySource source = new ToySource();
		
source.addContainerCases(new Cases() {
	
	boolean match(Tallboy tallboy, Toy toy) {
		System.out.println("The tallboys accept every toy, even the adult ones!");
		return true;
	}
	
	boolean match(Room room, Toy toy) {
		System.out.println("A room is not supposed to be a mess!");
		return false;
	}
	
	boolean match(Room room, Game game) {
		System.out.println("Games are the only exception, especially the ones that do not enter inside a tallboy!");
		return true;
	}
	
	boolean match(Box box, Toy toy) {
		System.out.println("A box is usually too small to contain toys!");
		return false;
	}
	
	boolean match(Box box, Car car) {
		System.out.println("1:43 scale cars enter inside a box!");
		return true;
	}
	
	boolean match(GoldenBox box, Toy toy) {
		System.out.println("What contains the golden box is unfortunately a secret!");
		return false;
	}
	
});

source.addContainer(new Tallboy("wood"));
source.addContainer(new Room("blue"));
source.addContainer(new Box(12));
source.addContainer(new GoldenBox(10));

source.update(new Car("red"));
source.update(new Doll(30));
source.update(new Game(4));
```

Thus the *match* methods are defined independently of the observer objects added to the *ToySource*.
This helps to clarify the rules based on the couples (*Container*, *Toy*).  

This example shows well the main difference with the standard implementation: multimethods are centered on sets of classes.
Each rule is a partition of the entire set of the possible tuples i.e. the couples in dimension two.

### Add a new state

As we already saw in other examples, it is possible to add an internal state like the *Weather*. 
In that case we would define a multimethod of dimension three and define rules on the triplets (*Weather*, *Container*, *Toy*).

To conclude, this example illustrates the main conceptual differences with the standard polymorphism.
Multimethods may not be seen as an extension of polymorphism but more as the implementation of **multiple dispatch**.
Indeed, where polymorphism is class-centered and does not help us to have a global view of the implementations or requires to draw diagrams, the multimethods
propose naturally the overview as the dispatch rules are explicitly defined.  
Moreover, the dispatch rules defined in dimension three are of course not possible with standard polymorphism.