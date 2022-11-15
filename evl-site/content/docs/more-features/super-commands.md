---
title: "Super commands"
description: Multimethods as super commands
weight: 1
---

In the first part of the documentation [the multimethods](/docs/the-multimethods.html), only *static match* methods were registered and dispatched.
The multimethods were thus *stateless*. In this section we show how to make them **stateful**.

### Dispatch to match methods
  
The idea is to define a class with members that we can 
access in the *match* methods. Let's take the class hierarchy defined in the [the multimethods](/docs/the-multimethods.html) and now define a *CounterA* class
that processes only classes of the *A* hierarchy i.e. *A* and *D*: 

```java
public class CounterA {

	public int count = 0;
	
	public void match(A obj) {
		count += 1;
	}

	public void match(D obj) {
		count += 2;
	}
}
```

The class is simply counting the objects by adding 1 for an object of type *A* and 2 for an object of type *D*.
Let's use them:

```java
CounterA counterA = new CounterA();

Method1<Void> method = new Method1<Void>().add(counterA);

method.invoke(a);
method.invoke(d);
	
System.out.println("Counter A: " + counterA.count);
```

This time we simply added the object *counterA* to the multimethod. The methods *match(A)* and *match(D)* are automatically added and we can apply the multimethod to the objects *a* and *d* but not the others because an exception would be thrown. Then we can access to the counter and we get 3 (1 + 2).

This example shows the dispatch to one object so that the multimethod can be considered as a *command*. In fact we can dispatch to any number of objects. Let's define another class counting the objects from the *B* hierarchy:

```java
public class CounterB {

	public int count = 0;
	
	public void match(B obj) {
		count += 10;
	}

	public void match(C obj) {
		count += 11;
	}
}
```

Let's instantiate it, add it to the multimethod and invoke this time through the iteration of an array of object:

```java
CounterB counterB = new CounterB();

method.add(counterB);
	
I[] objs = {a, b, c, d};
	
for (I obj : objs) {
	method.invoke(obj);
}

System.out.println("Counter A: " + counterA.count);
System.out.println("Counter B: " + counterB.count);

```

No surprise, the counter for *A* is 6 (3 + 1 + 2) and 21 for *B* (10 + 11). We showed how the multimethods can dispatch to a number of objects and be **stateful**, the objects being stateful.

### Dispatch to any method

It is not mandatory to add methods that have the name *match*. In fact it is possible to dispatch to any method but you will have to give the name.
To add all the *process* methods of the object *agent*:

```java
method.add(agent, "process");
```

To add the *process(A, int)* method of the object *agent*:

```java
method.add(agent, "process", A.class, int.class);
```

To add all the static *foo* methods of the class *Agent*:

```java
method.add(Agent.class, "foo");
```

To add the static *foo(B, C)* method of the class *Agent*:

```java
method.add(Agent.class, "foo", B.class, C.class);
```

This lets the freedom to dispatch to any method static or not of any object or class: the EVL multimethod library is flexible.