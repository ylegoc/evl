---
title: "Multimethod definition"
description: Proper definition
weight: 3
---

### Definition

An EVL multimethod is defined as follows:

* A multimethod of **dimension** ***D*** has a set *M* of *N* match methods *m*<sub>*j*</sub> with 0 <= *j* < *N*.
* The multimethod has a tuple of types *u*<sub>*k*</sub> of size *NV* called ***non-virtual*** **parameter types**.
* The multimethod has a **return type** ***R***.
* For each match method *m*<sub>*j*</sub>:
  * The first *D* parameter types is a tuple of types *t*<sub>*j*</sub> of size *D* called ***virtual*** **parameter types**.
  * The following parameter types **must be exactly** the *non-virtual* parameter types defined above.
  * An object of type *R* is returned or an object of type *RA* from which *R* **is assignable**. Otherwise an exception is thrown.
  * There is **optional data** that can be used for solving ambiguities. 
* The multimethod has a **method comparator**, that can be the *symmetric*, *asymmetric* or another distance comparator.
* The multimethod has a **cache strategy**.

### Examples

Suppose we have a multimethod of dimension 2 with 3 non-virtual parameter types (*String*, *int*, *boolean*) and the return type *int*.
The following match methods can be part of the multimethod or not:

| Match method                                       | Accepted | Error                                     |
|----------------------------------------------------|----------|-------------------------------------------|
| *int* m1(*A*, *A*, *String*, *int*, *boolean*)     | yes      | -                                         |
| *int* m1(*A*, *B*, *String*, *int*)                | no       | bad number of non-virtual parameter types |
| *int* m1(*D*, *C*, *String*, *float*, *boolean*)   | no       | bad non-virtual parameter types           |
| *void* m1(*A*, *C*, *String*, *int*, *boolean*)    | no       | bad return type                           |
