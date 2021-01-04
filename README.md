# EVL
EVL is a Java library that implements **multimethods** with a nice, simple and powerful interface.  
Let's start with the standard *Asteroid* example of dimension 2:

```java

class Asteroid {}
	
class Spaceship {}
	
Method2<Void> collide = new Method2<Void>().add(new Cases() {
			
	void match(Asteroid a, Asteroid b) {
		System.out.println("BAM! Two asteroids collide!");
	}
			
	void match(Asteroid a, Spaceship b) {
		System.out.println("BOUM! Asteroid collides spaceship!");
	}
			
	void match(Spaceship a, Asteroid b) {
		System.out.println("BOUM! Spaceship collides asteroid!");
	}
			
	void match(Spaceship a, Spaceship b) {
		System.out.println("CRASH! Two spaceships collide!");
	}
});
		
collide.invoke(new Asteroid(), new Asteroid());
collide.invoke(new Asteroid(), new Spaceship());
collide.invoke(new Spaceship(), new Asteroid());
collide.invoke(new Spaceship(), new Spaceship());
```

The *collide* multimethod dispatches the *match()* methods to a couple of objects depending on their type.  

The **multimethods** generalize the concept of polymorphism: 

* Dispatch to a tuple of objects.
* Dispatched methods are defined outside the class hierarchy.  

Although the multimethod concept is not new, the EVL implementation is flexible and solves many usual issues related to multimethods.
To build complex software, *Design Patterns* emerged on the basis of the simple polymorphism.
With EVL, *Design Patterns* can be reviewed in the light of multimethods.

Multimethods separate data from process and code using the *Visitor* pattern can be replaced with a multimethod.
The *State* pattern can also be reviewed by simply adding a state object to the parameters of the dispatched methods.

However defining multimethods can lead to different problems:

* How do we resolve a call if there is not a perfect match for the tuple of objects?
* How do we select the dispatched method if there are multiple matches?
* How to limit the memory footprint in case of a huge class hierarchy leading to a huge number of different cases?

These questions can be answered by the EVL implementation which provides an original concept of **method comparator** and a **cache strategy**.


