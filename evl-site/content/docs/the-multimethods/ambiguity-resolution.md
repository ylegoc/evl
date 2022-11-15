---
title: "Ambiguity resolution"
description: A question of priority
weight: 5
---

In the last section by changing the method comparator, we obtained an ambiguity in the resolution of the couple (*D*, *C*).
The first solution is to **add a new matching method** for the couple so that we obtain a perfect match. The new *foo2* looks like this:

```java
Method2<Float> foo2 = new Method2<Float>()
		.comparator(new SymmetricComparator())
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
	
	float match(D x, C y, String s) {
		return match((A)x, y, s);
	}
}); 
```

This time, calling *invoke()* does not fail for (*D*, *C*) and the two first calls still succeed.

```java
print(foo2, a, c); // 12.3
print(foo2, d, b); // 31.9
print(foo2, d, c); // 12.3
```

The second solution is to set special data to a *match()* method to give it **more priority** than the other. If we prefer to select *match(D, B, String)* then we can give it the priority 1:

```java
foo2.setData(Priority.valueOf(1), D.class, B.class);
						
print(foo2, a, c); // 12.3
print(foo2, d, b); // 31.9
print(foo2, d, c); // 31.9
```

But if we prefer to select *match(A, C, String)*, we can give **less priority** to *match(D, B, String)*:

```java
foo2.setData(Priority.valueOf(-1), D.class, B.class);
						
print(foo2, a, c); // 12.3
print(foo2, d, b); // 31.9
print(foo2, d, c); // 12.3
```

The *Priority* data class is a special class that comes with the method comparators and allows to force the choice when there is an ambiguity.

To finish, another possibility is to define your own method comparator class with your own data class, but this will be the subject of a next section. 