---
title: "Search for the best match"
description: Only the best survive
weight: 4
---

Now that we can define an ordering on the tuple of types by using the distance comparators and tuple of types distance, let's see how we search for the best matching method.
The algorithm is based on the definition of the multimethod in [multimethod definition](/docs/theory/multimethod-definition.html).

### Algorithm

We invoke *M* on a tuple of objects that has the tuple of types *t*.
The algorithm to select the method to apply is made up of two parts.

##### Calculate the compatible methods  

The subset *C* of *M* is defined as the set of compatible methods {*m*<sub>*j*</sub>} for which *d*<sub>*j*</sub> = distance(*t*<sub>*j*</sub>, *t*) is defined.

* If *C* is empty then the invoke call is a failure. There is **no matching** method.
* Otherwise we can continue for the search. 

##### Calculate the minimum methods
  
In *C* that is non-empty, we search for the minimum distances *d*<sub>*j*</sub> using the distance comparator.

* If there is a single method *m*<sub>*j*</sub> with minimum *d*<sub>*j*</sub> then the method is selected to be applied. If *t*<sub>*j*</sub> = *t*, it is the **perfect match**. Otherwise it is the **best match**.
* If there are many minimum *d*<sub>*j*</sub> that are equals, there is an **ambiguity** because we cannot select one method.

Notice that the calculation of the distance is fixed in EVL but the minimum methods set may vary depending on the comparator used. 

### Examples

As examples, we use the type hierarchy defined in [tuple of types distance](/docs/theory/tuple-distance.html).
To simplify, we do not show the return type and the non-virtual parameter types.  
We apply the couple (*D*, *C*) to multimethods of dimension 2 defined in each line of the table:


| Name | Match methods                                  | Compatible methods                             | Distances                          |  Comparator  |  Selected method |
|------|------------------------------------------------|------------------------------------------------|------------------------------------|--------------|------------------|
| *M1* | (*A*, *A*), (*A*, *D*)                         | -                                              | -                                  | asymmetric   | failure          |
| *M2* | (*A*, *A*), (*A*, *B*), (*A*, *C*)             | (*A*, *B*), (*A*, *C*)                         | (1, 1), **(1, 0)**                 | asymmetric   | (*A*, *C*)       |
| *M3* | (*A*, *B*), (*D*, *B*), (*A*, *C*)             | (*A*, *B*), (*A*, *C*), (*D*, *B*)             | (1, 1), (1, 0), **(0, 1)**         | asymmetric   | (*D*, *B*)       |
| *M4* | (*A*, *B*), (*D*, *B*), (*A*, *C*)             | (*A*, *B*), (*A*, *C*), (*D*, *B*)             | (1, 1), **(1, 0)**, **(0, 1)**     | symmetric    | ambiguity        |
| *M5* | (*A*, *B*), (*D*, *B*), (*A*, *C*), (*D*, *C*) | (*A*, *B*), (*A*, *C*), (*D*, *B*), (*D*, *C*) | (1, 1), (1, 0), (0, 1), **(0, 0)** | symmetric    | (*D*, *C*)       |

These are typical different cases:

* For *M1*, there is no compatible method thus no method can be applied.
* For *M2*, (*A*, *C*) is the single minimum. It is the best match but not the perfect match.
* For *M3*, (*D*, *B*) is the single minimum because it is less than (*A*, *C*) for the *asymmetric* comparator. It is the best match but not the perfect match.
* For *M4* which differs from *M3* by the comparator which is *symmetric*, there is an ambiguity because (*A*, *C*) and (*D*, *B*) are both minimum and equals for the comparator.
* For *M5*, the ambiguity is solved by adding (*D*, *C*) which is the perfect match. 

