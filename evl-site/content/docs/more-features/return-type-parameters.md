---
title: "Return type and parameters"
description: Check the signature
weight: 3
---

There are some special rules on the return type and parameters in the EVL library that must be presented.

For their definition see [multimethod definition](/docs/theory/multimethod-definition.html).
By default, an empty multimethod does not define the expected return type, and not the non-virtual parameter types.
They are defined by the **first method added**.

### Return type

Let's take the following hierarchy:

```java
public interface I {

}

public class A implements I {

}

public class B implements I {

	public int id = 7;
}
```

And a similar hierarchy:

```java
public interface RI {

	int get();
}

public class RA implements RI {

	@Override
	public int get() {
		return 3;
	}
}

public class RB implements RI {

	private int value;
	
	public RB(int value) {
		this.value = value;
	}

	@Override
	public int get() {
		return value;
	}
}
```

We want to define a factory that builds a *RA* object from an *A* object and a *RB* object from an *B* object.
Here is the multimethod:

```java
Method1<RI> method = new Method1<RI>()
		.returnType(RI.class)
		.add(new Cases() {
	
	RA match(A a) {
		return new RA();  
	}
	
	RB match(B b) {
		return new RB(b.id);  
	}
	
});
```

We defined the *match()* methods for *A* and *B* with different return types and we added a call to *returnType()* by passing the class *RI*. If we omit this call, we will get an exception in the *add()* call. Otherwise the return type is defined at the first insertion (*RA*) and the return type *RB* of the second *match()* is different from *RA*.

We can use the multimethod:

```java
I a = new A();
I b = new B();

System.out.println("A: " + method.invoke(a).get());
System.out.println("B: " + method.invoke(b).get());
```
  
And we will get 3 for *A* and 7 for *B*.

You surely noticed that the *RI* class is specified twice for the return type in our multimethod:

* By the generic type
* In the call to *returnType()*

The generic type is for the compilation phase and the other place is for the execution. Java does not provide a way to define both at once.

Notice that we defined a _virtual_ method by implementing *get()* from *RI*. We can avoid this by defining a multimethod instead:

```java
Method1<Integer> print = new Method1<Integer>().add(new Cases() {
	
	int match(RA a) {
		return 3;  
	}
	
	int match(RB b) {
		return b.value;  
	}
	
});

System.out.println("A: " + print.invoke(method.invoke(a)));
System.out.println("B: " + print.invoke(method.invoke(b)));
```

This example shows a chain of calls to multimethods.

### Non-virtual parameter types

To specify the non-virtual parameter types before the first insertion, the method *nonVirtualParameterTypes()* can be called:

```java
Method1<RI> method = new Method1<RI>()
		.returnType(RI.class)
		.nonVirtualParameterTypes(int.class, String.class);
		
method.add(new Cases() {
	
	RA match(A a, int i, String name) {
		return new RA();
	}
	
	RB match(B b, int i, String name) {
		return new RB(b.id + i + name.length());  
	}
	
});
```

Compared to the first factory multimethod, we added the parameters *i* and *name* so that our match methods have 3 parameters, the first one is the *virtual* parameter and the last ones are the *non-virtual* parameters. Notice that the type *Integer* is not compatible with *int* here.

The EVL library offers a way to **control the signature** of the *match()* methods, however these controls can only be done at **runtime**. 
For the return type it is possible to control at compilation but it is redundant with the control at runtime. These limitations are due to the Java language specifications. 
