---
title: "Interfaces"
description: Match an interface
weight: 2
---

In standard Java, it is not possible to define a method in an interface. With the EVL multimethods, it is now possible to do it.  

Let's take the following hierarchy:

```java
public interface IA {
	
	int getA();
}

public class A implements IA {
	
	public int getA() {
		return 1;
	}
}

public interface IB {
	
	float getB();
}

public class B implements IB {
	
	public float getB() {
		return 2.1f;
	}
}

public class C implements IA, IB {
	
	public int getA() {
		return 4;
	}
	
	public float getB() {
		return 35.9f;
	}
}
```

And now let's define the following multimethod:

```java
Method1<Integer> method = new Method1<Integer>().add(new Cases() {
	
	int match(IA a) {
		return a.getA();
	}
	
	int match(IB b) {
		return (int)b.getB();
	}
	
});
```

The multimethod defines methods for the interfaces *IA* and *IB* in which we can access to the methods *getA()* and *getB()*.
Let's apply the multimethod to the three objects *a*, *b* and *c*:

```java
Object a = new A();
Object b = new B();
Object c = new C();

System.out.println("A: " + method.invoke(a));
System.out.println("B: " + method.invoke(b));
System.out.println("C: " + method.invoke(c));
```

Without surprise we obtain 1 for *A* and 2 for *B*. However the call to *c* throws an exception: the call is ambiguous. It is not possible to decide between *IA* and *IC*.
Both interfaces are at distance 1 from *C* and the lexicographic comparator provides equality. Indeed we saw in the previous sections that the asymmetric comparator was providing less
ambiguities than the symmetric comparator. Here is the proof that the asymmetric comparator is not ambiguity free.

We resolve the ambiguity by adding a method for *C*:

```java
method.add(new Cases() {
			
	int match(C c) {
		return c.getA() + (int)c.getB();
	}
});
```

The ambiguity is resolved and we obtain 39 (4 + 35) for *C*.

The EVL multimethod library is **flexible** and allows to define methods for **interfaces**. However in case of multiple inheritance of interfaces, **ambiguities** can happen and even in the case of the default asymmetric comparator.