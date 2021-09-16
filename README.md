# EVL

EVL is a Java library that implements **multimethods** with a nice, simple and powerful interface.  
Let's start with an example of dimension two using standard Java classes:

```java
Method2<Void> who = new Method2<Void>().add(new Cases() {
				
	void match(Integer i, Integer j) {
		System.out.println("We are the integers " + i + " and " + j);
	}
	
	void match(String s, String t) {
		System.out.println("We are the strings " + s + " and " + t);
	}
	
	void match(Collection<?> c, String s) {
		System.out.println("We are a collection of size " + c.size() + " and a string " + s);
	}
	
	void match(List<?> l, String s) {
		System.out.println("We are a list of size " + l.size() + " and a string " + s);
	}
});

who.invoke("beautiful", "day");                            // match(String, String) is called
who.invoke(12, -25);                                       // match(Integer, Integer) is called
who.invoke(Arrays.asList("awesome" , "weather"), "today"); // match(List<?>, String) is called
who.invoke(Set.of("awful" , "weather"), "tomorrow");       // match(Collection<?>, String) is called
```

The *who* multimethod dispatches the couple of objects to the most appropriate *match()* method depending on their type.  

The **multimethods** generalize the concept of polymorphism: 

* **Multiple dispatch**: Polymorphism does not apply to a single class but a tuple of classes. Behaviors or match methods are defined on tuple of classes that can include interfaces. This is a new way to imagine behaviors. 

* **Enhance Object-Oriented Programming**: The multimethods allow to mix different class hierarchies, something that is not possible with standard polymorphism.

* **Functional approach**: Composition of multimethods is easy and allows functional programming with abstract objects, something that is not possible with standard polymorphism. It means that we can have many dispatches in the same function call by composing multimethods.


Although the multimethod concept is not new, the EVL implementation is flexible and solves many usual issues related to multimethods.
To build complex software, *Design Patterns* emerged on the basis of the simple polymorphism.
With EVL, *Design Patterns* can be reviewed in the light of multimethods.

Multimethods **separate data from process** and code using the *Visitor* pattern can be replaced with a multimethod.
The *State* pattern can also be reviewed by simply adding a state object to the parameters of the dispatched methods.

However defining multimethods can lead to different problems:

* How do we resolve the call if there is not a perfect match for the tuple of objects?
* How do we select the dispatched method if there are multiple matches?
* How to limit the memory footprint in case of a huge class hierarchy leading to a huge number of different cases?

These questions can be answered by the EVL implementation which provides an original concept of **method comparator** and a **cache strategy**.

