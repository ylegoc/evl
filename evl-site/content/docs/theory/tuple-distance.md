---
title: "Tuple of types distance"
description: Distance on tuple of types
weight: 1
---

The first prerequisite to understand how multimethods work is to know what is the tuple of types distance.

![Simple hierarchy](/images/hierarchy.png#center)

Here is a simple hierarchy, the one that we used in the examples of [the multimethods](/docs/the-multimethods.html) section.

### Type distance

Suppose that the type *X* is a super class (including interface) of *Y*.
The distance between *X* and *Y* is defined as the **minimum number of edges** between them in the type graph.
The distance is not defined for types that do not have an inheritance relation.

With our hierarchy, we have:

* distance(*A*, *A*) = 0
* distance(*I*, *A*) = 1
* distance(*I*, *B*) = 1
* distance(*B*, *C*) = 1
* distance(*I*, *C*) = 2
* distance(*I*, *D*) = 2
* distance(*A*, *C*) = *undefined*

### Tuple of types distance

We extend naturally the type distance to a tuple of types distance so that we have with our hierarchy:

In dimension 2:

* distance((*A*, *B*), (*A*, *B*)) = (0, 0)
* distance((*A*, *B*), (*A*, *C*)) = (0, 1)
* distance((*A*, *B*), (*D*, *C*)) = (1, 1)
* distance((*I*, *B*), (*D*, *C*)) = (2, 1)
* distance((*A*, *B*), (*C*, *C*)) = *undefined*

In dimension 3:

* distance((*A*, *B*, *A*), (*D*, *C*, *A*)) = (1, 1, 0)
* distance((*I*, *B*, *B*), (*D*, *C*, *C*)) = (2, 1, 1)

Now that we know how to define a distance on tuple of types of various dimensions, we can define comparators.