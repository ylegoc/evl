---
title: "Switch"
description: Multimethods as switch
weight: 4
---

The EVL library provides the *Switch* class which is a **syntactic sugar** for a multimethod of dimension 1 with *void* return type.

Let's take a simple hierarchy:

```java
public class A {
	
	public int a = 2;
}

public class B extends A {
	
	public B(int a) {
		this.a = a;
	}
}
```

The *Switch* class implements a general switch construct based on the dynamic type. We add a non-virtual parameter to show that it is accepted :

```java
Switch test = Switch.with(new Cases() {
				
	void match(Integer i, int v) {
		System.out.println("Integer " + (i + v));
	}
	
	void match(String s, int v) {
		System.out.println("String " + s + v);
	}
	
	void match(A a, int v) {
		System.out.println("A " + (a.a + v));
	}
});
```

We can use it:

```java
test.invoke(Integer.valueOf(12), 3);
test.invoke(new String("beautiful"), 11);
test.invoke(new B(5), 4);
test.invoke(Float.valueOf(13.1f), 5);
```
  
Everything will be fine except for the last invocation: there is no compatible method because a *Float* object cannot be cast into an *Integer* object.

The *Switch* class can be used anywhere with any **basic types**, however only in their ***Object*** **form** preventing from number casting.
This rule also applies to standard multimethods.