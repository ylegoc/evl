---
title: "The base transform"
description: From standard polymorphism to a multimethod
weight: 1
---

In this section, we show how to define a multimethod that does the same thing as a class hierarchy with overriden methods.
Let's start with the following class hierarchy (to be more concise, packages are not defined here):

```java
public interface I {

	int foo(String s, int i);
}

public class A implements I {

	public int foo(String s, int i) {
		return i;
	}
}

public class B implements I {

	public int foo(String s, int i) {
		if (s.equals("square")) {
			return i * i;
		}
		return 0;
	}
}

public class C extends B {

}
```

Let's define a *print()* method that is using them:

```java
public static void print(I obj) {
		
	int res = obj.foo("square", 3);
	System.out.println(obj.getClass().getName() + " -> " + res);
}
```

Now let's instantiate the classes and use them:

```java
I a = new A();
I b = new B();
I c = new C();

print(a); // 3
print(b); // 9
print(c); // 9
```

In the *print()* method, *obj* has static type *I* but has real type *A*, *B*, *C* depending on the call. The call to *foo()* on *obj*
is resolved at runtime and the method applied is chosen depending on the real type of *obj*:

* *A*: *foo()* is redefined for *A* so it is applied.
* *B*: *foo()* is redefined for *B* so it is applied.
* *C*: *foo()* is not redefined for *C*, *B.foo()* is applied as the most specialized method for *C*.

At runtime, the most specialized redefinition of the *foo()* method is chosen.
The Java polymorphism is a *single dispatch*: the caller type is used to choose the method to apply.

Let's see how to obtain the same polymorphism with an EVL multimethod.
Consider the following instantiated class:

```java
Method1<Integer> foo = new Method1<Integer>().add(new Cases() {
			
	int match(A a, String s, int i) {
		return i;
	}
	
	int match(B b, String s, int i) {
		if (s.equals("square")) {
			return i * i;
		}
		return 0;
	}
});
```

We instantiate a *Method1&lt;Integer&gt;* class with match cases corresponding to the two *foo()* implementations for *A* and *B*.
That's all and now let's see that the dispatch is exactly the same as for the class polymorphism. Let's define another *print()* method:

```java
public static void print(Method1<Integer> foo, I obj) throws Throwable {
		
	int res = foo.invoke(obj, "square", 3);
	System.out.println(obj.getClass().getName() + " -> " + res);
}
```

Let's call *print()* with the *foo* multimethod object:

```java
try {
		print(foo, a); // 3
		print(foo, b); // 9
		print(foo, c); // 9
}
catch (Throwable e) {
	e.printStackTrace();
}
```

We obtain the same results. That's simple, isn't it? We simply associated a *match()* method for each type *A* and *B*. The multimethod dispatches the call to the *match()* method corresponding to the runtime type of *obj*. The same rule is applied to select the best matching
*match()* method in the case of the *C* object.

Notice that we defined a multimethod of type *Method1&lt;Integer&gt;* because we dispatch using one parameter and return an *Integer* value. The dispatch is always done using the first parameters that we call virtual parameters. In our case the first parameter of the *match()* methods is the virtual parameter (*A* or *B*) and the two others are the non-virtual parameters (*String* and *int*).

We needed to catch a *Throwable* exception because the call to *invoke()* can fail by an exception thrown by the multimethod or the *match()* method called.

Now that the multimethod does exactly the same dispatch as the former class polymorphism, we can remove the *foo()* methods from the class hierarchy. We call it the **base transform**.

With multimethods, polymorphism is replaced by a runtime dispatch. That means that method declaration is no longer necessary in interfaces that only are a way to classify classes. This flexibility allows to define as many multimethods as we want.
