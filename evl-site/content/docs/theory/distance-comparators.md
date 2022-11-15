---
title: "Distance comparators"
description: Comparators on tuples of integers
weight: 2
---

The second prerequisite to understand how multimethods work is to know what are the comparators for the tuple of types distance.

There are many possible ways to define comparators on tuple of integers. We follow the Java convention on return values of compare methods so that for *x* and *y* tuple of integers:

* If *x* < *y*, return -1
* If *x* > *y*, return 1
* If *x* == *y*, return 0

In the EVL library, we provide two useful comparators on integer tuples.
The tuples have size *D* and value at dimension *i* is accessed by *x*[*i*] and *y*[*i*]. 

### Asymmetric comparator

The **lexicographic** operator also called the **asymmetric** operator:  
For each *i* with 0 <= *i* < *D*
  
  * if *x*[*i*] < *y*[*i*] then *x* < *y*
  * if *x*[*i*] > *y*[*i*] then *x* > *y*
  * if *x*[*i*] == *y*[*i*] then continue with *i+1*
  
At the end, *x* == *y* if for all *i*, *x*[*i*] == *y*[*i*]

Examples:

* (1, 2, 1) == (1, 2, 1)
* (1, 0, 1) < (1, 2, 1)
* (0, 1, 2) > (0, 1, 1)
* (1, 0, 2) < (1, 2, 1)

Notice that in the last example, *x* < *y* although *x*[2] > *x*[2]. This is because *x*[0] == *y*[0] but *x*[1] < *y*[1].

### Symmetric comparator
  
The **product** operator also called the **symmetric** operator:

  * *x* < *y* if for all *i* with 0 <= *i* < *D*, *x*[*i*] < *y*[*i*]
  * *x* > *y* if for all *i*, *x*[*i*] > *y*[*i*]
  * *x* == *y* otherwise

Examples:

* (1, 2, 1) == (1, 2, 1)
* (1, 0, 1) < (1, 2, 1)
* (0, 1, 2) > (0, 1, 1)
* (1, 0, 2) == (1, 2, 1)

Notice that in the last example, *x* == *y* because *x*[1] < *y*[1] but *x*[2] > *y*[2].

### Other comparators

The *asymmetric* and *symmetric* comparators presented are the two opposite ways to define comparators on tuples:

* Either the dimensions are taken one by one: the *asymmetric* comparator
* Either the dimensions are taken as a whole: the *symmetric* comparator

However many other comparators can be defined e.g. by **mixing** the *symmetric* and *asymmetric* on different dimensions. 

### Refined comparators

In EVL, the *symmetric* and *asymmetric* operators are defined but they are refined to be able to manage ambiguities.
Indeed it is possible to give a priority to each element that is compared in case of equality on the distance.
An example on how to use priority is given in [ambiguity resolution](/docs/the-multimethods/ambiguity-resolution.html).
