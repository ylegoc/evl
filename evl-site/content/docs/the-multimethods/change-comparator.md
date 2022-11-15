---
title: "Change comparator"
description: Tune the multimethod
weight: 4
---

In the last section we saw how the dispatch is made by default for a multimethod of dimension two.
The default comparator is the **asymmetric** using the lexicographic order to select the best match.
In this section we show that the default method comparator can be changed. 
The library provides another method comparator which is the **symmetric** comparator.
Let's see how it behave on a simple example.

We define a multimethod with the symmetric comparator:

```java
Method2<Float> foo2 = new Method2<Float>()
		.symmetricComparator()
		.add(new Cases() {
	
	float match(A x, A y, String s) {
		return x.id;
	}
	
	float match(A x, B y, String s) {
		if (s.equals("multiply")) {
			return x.id * y.fd;
		}
		return 0;
	}
	
	float match(A x, C y, String s) {
		if (s.equals("add")) {
			return x.id + y.fd;
		}
		return 0;
	}
	
	float match(D x, B y, String s) {
		if (s.equals("add")) {
			return 2 * x.id + 3 * y.fd;
		}
		return 0;
	}
});
```

We configured the multimethod by simply calling the *symmetricComparator()* method.
Let's try it:

```java
try {
	print(foo2, a, c); // 12.3
	print(foo2, d, b); // 31.9
	print(foo2, d, c); // error
}
catch (Throwable e) {
	e.printStackTrace();
}
```

There is still a perfect match for the (*A*, *C*) and (*D*, *B*) however for the couple (*D*, *C*), the two compatible methods *match(A, C, String)* and *match(D, B, String)*
lead to an exception in the *invoke()* call.  
Indeed to select a couple of types, the **symmetric** comparator is using a product order and the couple must be "better" or "equals" for each dimension.
For the couple of objects (*D*, *C*), *match(D, B, String)* is better than *match(A, C, String)* for the first dimension but *match(A, C, String)* is better than *match(D, B, String)*
for the second dimension.   
Then the comparator considers *match(D, B, String)* and *match(A, C, String)* equals for (*D*, *C*) so that the multimethod throws an exception: the call is **ambiguous**.    

In the next section, we see how to resolve ambiguous calls.