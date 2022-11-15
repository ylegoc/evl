---
title: "Invoke with cache"
description: A cache strategy
weight: 5
---

The invoke sequence is presented in this section. We introduce the cache strategy.

### Workflow

![Simple hierarchy](/images/invoke.png#center)

When the multimethod is invoked on objects *v* with a tuple of virtual types *t* and *nv* with a tuple of types *u*, we first search for the best match method for *t* as presented in [search for the best match](/docs/theory/search-best-match.html):  

* If the best match method is found then it is applied to the passed arguments.  
* If the method is not found - no compatible method or ambiguity - an exception is thrown.  

If the multimethod is unchanged between two calls on different objects that have the same tuple of virtual types, then the same best match method will be called.
We can store it in a **cache**, so that the search for the best match is only done once for a tuple of types.
However the cache must be cleared when the multimethod is changed e.g. when adding a new method or setting optional data.

To print the content of the cache that can be useful when debugging, use the method *printCache()*.

### Bounded or unbounded?

By default, the cache implementation is **unbounded**: the cache can grow without limits. However the number of potential tuples in the cache is the number of classes power the number of dimensions, which can be big. In that case if we want to ensure that the cache will not exceed a size, we can use a **bounded** cache.
    
As an example we define the multimethod with a bounded cache of size 128:

```java
Method2<Float> foo2 = new Method2<Float>().boundedCache(128);
```

Moreover the cache must be **thread-safe** to ensure that invoke is thread-safe. And if you're not happy with the default implementations, you can still redefine your own cache strategy.