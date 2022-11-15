---
title: "Tree walker"
description: Walk without falling
weight: 12
---

This example is showing how to use a multimethod to traverse tree structures.

We start with a *Print* class that prints a hierarchical string view of a tree. Then we generalize by separating the traversal from the process.
As example, we show two process classes: *TreePrinter* that prints the same content than *Print* and *TreeCarInBoxCounter* that simply counts the *Car* objects in *Box* objects.  

The tree structure is based on the classes defined in the [preliminaries](/docs/examples/preliminaries.html).

### Print implementation 

The *Print* class is based on the [open access](/docs/more-features/open-access.html) feature.
As a common *XML* file, the depth of a node is represented by the number of space indents.
The idea is to pass a *Space* object that holds the current space and increment or decrement it.

First we define the *Space* class:

```java
public class Space {
	
	String space = "";
	
	public void increment() {
		space += "  ";
	}
	
	public void decrement() {
		space = space.substring(2);
	}
	
	public String get() {
		return space;
	}
}
```

Then the *Print* class can be defined. Inside it, we define a multimethod of dimension one with restrictions on the non-virtual parameters and the return type:

```java
public class Print {

	private static Method1<Void> method = new Method1<Void>()
			.returnType(Void.class)
			.nonVirtualParameterTypes(Space.class);

	public static Method1<Void> method() {
		return method;
	}
	
	public static void print(Object object) throws Throwable {
		method.invoke(object, new Space());
	}
}
```

The call to *invoke()* is encapsulated into the *print()* call so that we can initialize the recursion with a fresh new *Space* object passed as the second parameter that is the non-virtual parameter. Notice that the *Print* class does not know any class and cannot be invoked. We must define match methods.

Let's use it with match methods on our example classes:

```java
Print.method().add(new Cases() {
			
	void match(Container container, Space space) {
		
		System.out.println(space.get() + container);
		
		space.increment();
		
		for (Element element : container.getElements()) {
			try {
				Print.method().invoke(element, space);
			}
			catch (Throwable e) {
				System.out.println(space.get() + "?");
			}
		}
		
		space.decrement();
	}
	
	void match(Element element, Space space) {
		System.out.println(space.get() + element);
	}
});
```

We benefit from the default *toString()* method already defined onto all the example classes.
Thus we simply define two match methods: on the *Container* class and the *Element* class.  

The *Container* class is the abstract class that owns the *Element* children. We print the object with the space and increments it before invoking recursively the *Print* on the children.
When the process of the children is terminated we can decrement the space. 

For the *Element* class, it is simply the print of the object with space.

We can test it on a filled *Room* object:

```java
Room room = RoomBuilder.build();
		
Print.print(room);
```

### Generalization of the traversal

The implementation of *Print* is specialized and mixes the traversal of the tree structure of our *Element* objects and their process. In this section we propose a generalization that separates process from traversal using a tree walker. Our tree walker abstraction is based on the following methods:

* *enter*: Method called when entering a node i.e. an *Element* in our case.
* *traverse*: Method called after entering the node, implementing the traversal algorithm.
* *leave*: Method called after traversing the node.
 
If now you begin to master the EVL library, I'm sure that you know that the *enter*, *traverse* and *leave* methods are multimethods in our implementation and you're right.

Let's define the *TreeWalker* class with the three multimethods:

```java
public class TreeWalker {
	
	private Method1<Void> traverse = new Method1<Void>()
			.returnType(Void.class)
			.nonVirtualParameterTypes(TreeWalker.class);
	
	private Method1<Void> enter = new Method1<Void>()
			.returnType(Void.class)
			.nonVirtualParameterTypes();
	
	private Method1<Void> leave = new Method1<Void>()
			.returnType(Void.class)
			.nonVirtualParameterTypes();
	
	public Method1<Void> traverse() {
		return traverse;
	}
	
	public Method1<Void> enter() {
		return enter;
	}
	
	public Method1<Void> leave() {
		return leave;
	}
	
	public void walk(Object object) throws Throwable {
		enter.invoke(object);
		traverse.invoke(object, this);
		leave.invoke(object);
	}
}
```

You can notice that the *traverse* multimethod differs from *enter* and *leave* by the fact that it has a *TreeWalker* non-virtual parameter. It is necessary to organize the recursion because our tree traversal is recursive. The *TreeWalker* implementation uses the strategy pattern as the *walk()* method is calling the *enter*, *traverse* and *leave* multimethods.
Notice that this implementation is general and could be reused any other context.

Now we can specialize the traversal. Let's define the Depth-First Search traversal methods for our *Element* objects:

```java
public class TreeTraversal {

	public void traverse(Container container, TreeWalker walker) throws Throwable {
		
		for (Element element : container.getElements()) {
			walker.walk(element);
		}
	}
	
	public void traverse(Element element, TreeWalker walker) {
	}
}
```

This class is defining the different *traverse* implementations. We find the *TreeWalker* parameter in the two implementations, called to continue the recursion in case of a *Container* object.

The *enter* and *leave* implementations are defined into the process class *TreePrinter*:

```java
public class TreePrinter {

	private Space space = new Space();
	
	public void enter(Element element) {
		
		System.out.println(space.get() + element);
		
		space.increment();
	}
	
	public void leave(Element element) {
		space.decrement();
	}
}
```

We find a *Space* object to manage the space indents. Then we can define our concrete *TreeWalker* configured with *TreeTraversal* and *TreePrinter* objects and apply it to the previously defined *Room* object:

```java
TreeWalker walker = new TreeWalker();
			
TreeTraversal traversal = new TreeTraversal();
TreePrinter printer = new TreePrinter();

walker.traverse().add(traversal, "traverse");
walker.enter().add(printer, "enter");
walker.leave().add(printer, "leave");

walker.walk(room);
```

In this implementation, we preferred to give the names *traverse*, *enter* and *leave* to the process methods rather than the default *match* name because it clarifies what they do and allows to have *enter* and *leave* in the same class.

### Count the cars in boxes

Let's show that we can use our *TreeWalker* and *TreeTraversal* classes to define another algorithm on the *Element* tree objects. Now we want to count the number of *Car* objects in each *Box* object. Thus we define the class *TreeCarInBoxCounter* that prints the results:

```java
public class TreeCarInBoxCounter {

	private Deque<Integer> counters = new ArrayDeque<Integer>();
	
	public void enter(Element element) {
	}
	
	public void enter(Box box) {
		counters.push(0);
	}
	
	public void enter(Car car) {
		Integer count = counters.pop();
		count++;
		counters.push(count);
	}
	
	public void leave(Element element) {
	}
	
	public void leave(Box box) {
		int count = counters.pop();
		
		System.out.println(box + " contains " + count + " cars");
	}
}
```

We use it like the previous *TreePrinter* class:

```java
TreeWalker walker = new TreeWalker();
			
TreeTraversal traversal = new TreeTraversal();
TreeCarInBoxCounter counter = new TreeCarInBoxCounter();

walker.traverse().add(traversal, "traverse");
walker.enter().add(counter, "enter");
walker.leave().add(counter, "leave");

walker.walk(room);
```

Easy right? And very flexible.

In this example we restricted the three multimethods to the dimension one but of course we could have added more dimensions if we wanted to pass other objects that may change the process.
For instance we could have added another state object as seen in the [strategy](/docs/examples/strategy.html) example.

This example shows once again that multimethods are **adaptable** to any programming problem and allow to have a good separation between class definitions and their behaviors: here the *Element* classes and the tree process methods. 
