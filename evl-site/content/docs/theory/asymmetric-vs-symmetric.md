---
title: "Asymmetric vs symmetric"
description: Democratic or not?
weight: 6
---

To finish the theory part, we can rapidly discuss about the asymmetric and symmetric comparators in multiple dimensions. 
For their definition, refer to [distance comparators](/docs/theory/distance-comparators.html).

On one side, the *asymmetric* comparator is **easy** to understand and predict. The dimensions are taken one by one to find the best match. The ambiguities can only come from a multiple inheritance of interfaces.  
On the other side, the *symmetric* comparator is **democratic**. All the dimensions have the same weight in the final selection. However more democracy means more ambiguities and more difficulties to select one best match. 

In [search for best match](/docs/theory/search-best-match.html), the multimethods given as example *M3* and *M4* have the same methods defined but have a different comparator. *M4* will have an ambiguity while *M3* won't.

The choice of the type of comparator may not only guided by the ease to treat all the cases. The implementation of an operator that is fundamentally symmetric e.g. *equals* may deliberately choose the *symmetric* operator.

In EVL, the default distance comparator is the *asymmetric* comparator. We think that it is better to provide the easier comparator and avoid the user to resolve ambiguities. However it is possible to switch to the *symmetric* comparator as an implementation is provided.

