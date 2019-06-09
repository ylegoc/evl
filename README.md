# EVL
EVL is a Java library that implements generalized multimethods with a nice and simple interface.

Let's start with the standard Asteroid example of dimension 2:

```java
public class Collider {

    public static class Asteroid {}
	
    public static class Spaceship {}
	
    public static void run() throws Throwable {
	
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
    }
}
```

The `collide` multimethod dispatches the `match` methods to a couple of objects depending on their type.  

The **multimethods** generalize the concept of polymorphism: 
* Dispatch to a tuple of objects.
* Dispatched methods can be defined outside the class hierarchy sharing the same interface.  

To build complex software, *Design Patterns* emerged based on the use of the simple polymorphism.
By introducing a new Object-Oriented concept, *Design Patterns* are reviewed in the light of multimethods.

Multimethods separate data from process and the *Visitor* pattern becomes immediately obsolete.
The *State* pattern can also be put away because by simply adding a state object to the parameter of the dispatched methods.

Defining multimethods can lead to different problems:
* How do we resolve the call if there is not a perfect match for the tuple of objects?
* How do we select the dispatched method if there are multiple matches?
* How to limit the memory footprint in case of a huge class hierarchy leading to a huge number of different cases?

These questions can be answered by the EVL implementation which provides a concept of method comparator and a cache strategy.

