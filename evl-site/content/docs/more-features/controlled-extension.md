---
title: "Controlled extension"
description: Override or not?
weight: 7
---

By default, EVL multimethods are permissive and allow to override and extend existing match methods. However it is possible to restrict and emulate the "final" qualifier.

First it is possible to define a multimethod that is **not overridable**:

```java

Method1<Integer> method = new Method1<Void>()
		.notOverridable()
		.add(new Cases() {
	
	int match(A a) {
		return 1;  
	}
	
	int match(B b) {
		return 2;  
	}
	
});
```

This way, trying to add a new match method for *A* or *B* will lead to the throw of a *MethodNotAddedException*:

```java

try {
	method.add(new Cases() {
	
		int match(A a) {
			return 1;  
		}
}
catch (Throwable e) {
	e.printStackTrace();
}
```

Second it is possible to make the multimethod **final** meaning that it is not possible to extend it anymore:

```java

method.setFinal();
```

So that add a new match method will lead to the throw of a *MethodNotAddedException*:

```java

try {
	method.add(new Cases() {
	
		int match(C c) {
			return 3;  
		}
}
catch (Throwable e) {
	e.printStackTrace();
}
```

Once the multimethod is set final, it is impossible to go back and it will remain final for the rest of its existence.
