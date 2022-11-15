---
title: "More dimensions"
description: In the hyperspace
weight: 6
---

In this section, we see that it is possible to define a multimethod with more than two dimensions.
We define a multimethod with four dimensions:

```java
Method4<String> foo4 = new Method4<String>().add(new Cases() {
				
	String match(I x, I y, I z, I t) {
		return "default";
	}
	
	String match(A x, A y, A z, A t) {
		return "A, A, A, A = " + x.id + ", " + y.id + ", " + z.id + ", " + t.id;
	}
	
	String match(A x, B y, C z, D t) {
		return "A, B, C, D = " + x.id + ", " + y.fd + ", " + z.fd + ", " + t.id;
	}
});
```

Note that this time we defined some kind of a default *match()* method for the hierarchy of types containing the root interface *I* and the classes *A*, *B*, *C* and *D*.
With such a definition we ensure that for any combination of types in that hierarchy, there will be a matching *match()* method.

To test it, we define in a *print()* method:

```java
public static void print(Method4<String> foo4, I obj1, I obj2, I obj3, I obj4) throws Throwable {
		
	String res = foo4.invoke(obj1, obj2, obj3, obj4);
	System.out.println(obj1.getClass().getName() + ", "
				+ obj2.getClass().getName() + ", " 
				+ obj3.getClass().getName() + ", " 
				+ obj4.getClass().getName() + " -> " + res);
}
```

And we call it with some tuples:

```java
print(foo4, a, a, a, a); // A, A, A, A... 
print(foo4, d, a, c, b); // default
print(foo4, a, b, c, d); // A, B, C, D...
```

The tuples with dynamic types (*A*, *A*, *A*, *A*) and (*A*, *B*, *C*, *D*) have a perfect match however (*D*, *A*, *C*, *B*) has not and the default *match()* method is called.

Note that EVL multimethods are currently **limited to seven dimensions** but it is just because it was necessary to set a limit. However it is very easy to extend to any number of dimensions.