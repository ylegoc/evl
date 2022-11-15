---
title: "Why use EVL?"
description: Convincing reasons to use EVL
weight: 11
---

Here comes the end of the proof. That is where we expose the advantages and the disadvantages of the EVL library. 
Indeed even if the multimethods concept is quite old, until now, there is no implementation that became successful. So why use the EVL multimethods?

### Why use multimethods?

The multimethods are the marriage between Object-Oriented Programming and Functional Programming.

**Multiple dispatch**  
	Polymorphism does not apply to a single class but a tuple of classes.
	Behaviors or match methods are defined on tuple of classes that can include interfaces. This is a new way to imagine behaviors. 

**Enhance Object-Oriented Programming**  
	The multimethods allow to mix different class hierarchies, something that is not possible with standard polymorphism.

**Functional approach**  
	Composition of multimethods is easy and allows functional programming with abstract objects, something that is not possible with standard polymorphism.
	It means that we can have many dispatches in the same function call by composing multimethods.

### What we gain with multimethods

**Separate data and behavior**  
	The clear separation between data i.e. classes and behavior i.e. methods allows a simple code design.
	Standard polymorphism defined through a hierarchy of classes which implement an interface is obsolete.
	Only methods or functions are defined and polymorphism is achieved by method overrides or *match* methods in EVL, separated from the classes that define the data.

**Flexibility and extensibility**  
	Multimethods that are not intrusive are extended at runtime and it is very easy to add a new match method.
	Dispatch is possible on both methods and associated objects and static methods.
	As a consequence, it is possible to define as many behaviors as we need. Their number is not fixed by interfaces of a class hierarchy.
	
**Design**  
	Code using design patterns based on polymorphism can be rewritten: Visitor, State, Strategy, etc. 
	Using multimethods make them obsolete e.g. states can be added easily to a multimethod.
	The *instanceof* operator should be forgotten as it can be replaced with a multimethod.
	
### What we gain with EVL multimethods	

**Method comparator**  
	A new concept of method comparators including asymmetric, symmetric and custom is provided helping to refine the multiple dispatch choice and solve specific issues like ambiguities. 
	
**Cache strategy**  
	To speed up the calls, a cache strategy is available. If the number of dimensions increases and the class hierarchies are big, the number of possible tuples of class can be big.
	Then a special bounded cache can be used to ensure to not have memory issues.	

### What we have to take care with multimethods

**Dynamic definition**  
	It has been presented as an advantage however it can also be less clear as the language becomes more dynamic with less static information.
	For instance it is more difficult to know what is the content of a multimethod as it depends on the registration of match methods at runtime.
	Whereas the list of available methods of a class is a static information easy to access with high-level editors.
			
**Multiple dimensions**  
	This feature can be difficult to apprehend. Adding a match method is easy however their number should not be too large.
	Ambiguities can happen and solving them may not be simple.

### In practice

In theory, every method of a class can be replaced with a multimethod.
However, in practice it is up to you to decide when using a multimethod because we have not yet enough experience.
What we know is that you have to think differently, to think in terms of sets of tuple of classes. 
