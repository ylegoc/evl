---
title: "Errors"
description: List of errors
weight: 8
---

In this section we summarize all the possible exceptions that can occur in different methods of a multimethod:

Exception                                  | Methods                    | Cause
-------------------------------------------|----------------------------|------
BadNumberOfVirtualParameterTypesException  | add()                      | The number of virtual parameter types of the method added is different from the one defined in the multimethod
BadNonVirtualParameterTypesException       | add()                      | The virtual parameter types of the method added are different from those defined in the multimethod
BadReturnTypeException                     | add()                      | The return type of the method added is different from the one defined in the multimethod
MethodNotAddedException                    | add()                      | The method cannot be added
IllegalArgumentException                   | invoke()                   | A virtual argument is null
NoMatchingMethodException                  | invoke(), check()          | There is no matching method for the requested tuple of typles
AmbiguousMethodException                   | invoke(), check()          | It is not possible to select one matching method for the requested tuple of typles
UnexpectedException                        | invoke(), check()          | The method is not accessible
*User exception*                           | invoke()                   | The called matching method throws an exception

For exceptions occuring in the *add()* methods, we remind that: 

* The return type is defined by a call to *returnType()* or by the one of the first added method.
* The non virtual parameter types are defined by a call to *nonVirtualParameterTypes()* or by those of the first added method.
* The number of virtual parameter types is defined by the type of the multimethod i.e. *Method1*, *Method2*, *Method3*, *Method4*, *Method5*, *Method6*, *Method7*.