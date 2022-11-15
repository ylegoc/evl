---
title: "Best match"
description: Search for perfection
weight: 3
---

In the last section we defined our first multimethod of dimension two. In this section we will see how the best matching method is selected.
Let's redefine our multimethod by adding a *match()* method for (*A*, *C*):

```java
Method2<Float> foo2 = new Method2<Float>().add(new Cases() {
			
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
});
```

We apply it to the objects (we do not put the necessary *try/catch* here):

```java
print(foo2, a, b); // 0
print(foo2, a, c); // 12.3
```

For the couple (*A*, *B*), there is only one compatible method which is *match(A, B, String)*, however for (*A*, *C*) the method *match(A, C, String)* is also compatible meaning that there are two compatible methods. The multimethod selects the **best match**, that is *match(A, C, String)* which is in fact the **perfect match** for (*A*, *C*).

Let's modify the class hierarchy by adding a *D* class that inherits *A* and instantiated by the *d* variable:

```java
public class D extends A {

}

I d = new D();
```

Let's modify the multimethod to take into account *D*:

```java
Method2<Float> foo2 = new Method2<Float>().add(new Cases() {
			
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

Let's apply it to a new couple (*D*, *C*):

```java
print(foo2, d, c); // 31.9
```

This time, we still have two compatible methods *match(A, C, String)* and *match(D, B, String)* but none of them is the perfect match.
In this case, the multimethod selects *match(D, B, String)* because by default it selects the best match dimension by dimension from left to right.
Indeed by default the multimethod is configured with a **method comparator** using the lexicographic order.
