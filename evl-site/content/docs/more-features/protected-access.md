---
title: "Protected access"
description: Restrict the use
weight: 6
---

As EVL multimethods are objects, they can be either **global** i.e. accessible anywhere or **local** and protected to a class hierarchy. It will depend of course on what you want to do with your multimethod. We present the local access here where a multimethod is a **protected** member of a class. 

As in [open access](/docs/more-features/open-access.html), we need a simple class hierarchy:

```java
public class A {
	
	public int a = 2;
}

public class B extends A {
	
	public B(int a) {
		this.a = a;
	}
}
```

But this time, the multimethod definition is done inside a class:

```java
public class Class {

	protected Method1<Integer> m = new Method1<Integer>();
	
	public Class() {
		m.add(this, "fooMatch");
	}
	
	protected int fooMatch(A a) {
		return a.a;
	}
	
	public int foo(A a) {
		return m.invoke(a);
	}
}
```

The *Class* class is the first class of a hierarchy to use a multimethod:  

* The multimethod *m* is a member.
* The method *foo(A)* is the public access to the multimethod. It simply forwards the call to *m*.  
* The method *fooMatch(A)* is the match method for *A*.
* In the constructor, *m* is provided access to the protected and private methods of *Class* and its hierarchy.
* In the constructor, *m* is feed with all the methods named *fooMatch()* called with the object *this*. 

The class can be directly tested with objects of type *A* and *B*:

```java
A a = new A();
B b = new B();

Class c1 = new Class();

System.out.println("c1.foo on a: " + c1.foo(a));
System.out.println("c1.foo on b: " + c1.foo(b));
```

Now let's extend *Class* into the class *ExtendedClass* which defines a new match method for *B*:

```java
public class ExtendedClass extends Class {
	
	private int i = 4;
	
	public ExtendedClass() {
	}
	
	protected int fooMatch(B b) {
		return b.a + i + fooMatch((A)b);
	}
}
```

This implementation shows:  

* The method *fooMatch(B)* can use the *i* member as well as call *fooMatch(A)* defined in *Class*.
* There is nothing to do in the constructor. The *Class* constructor is called before and all the *fooMatch()* methods are added to *m* including *fooMatch(B)*.


We can test it:

```java
ExtendedClass e1 = new ExtendedClass();
	
System.out.println("e1.foo on a: " + e1.foo(a));
System.out.println("e1.foo on b: " + e1.foo(b));
```

This time the call to *b* is dispatched to *fooMatch(B)*.

### Extend to the dimension two

Now we want to extend the internal multimethod to dimension two. We define an *Operator* hierarchy that will be used as second *virtual parameter* to the multimethod.
The definition of *Class2* is a bit longer:

```java
public class Class2 {

	protected Method2<Integer> m = new Method2<Integer>();
	
	protected static class Operator {};
	protected static class Add extends Operator {};
	protected static class Multiply extends Operator {};
	
	protected Operator operator = new Add();
	
	protected void setAddOperator() {
		operator = new Add();
	}
	
	protected void setMultiplyOperator() {
		operator = new Multiply();
	}
	
	protected int fooMatch(Add op, A a) {
		return a.a + 2;
	}
	
	protected int fooMatch(Multiply op, A a) {
		return a.a * 2;
	}
	
	public Class2() {
		
		m.access(Class2.class);
		m.add(this, "fooMatch");
	}
	
	public int foo(A a) throws Throwable {
		return m.invoke(operator, a);
	}
}
```

It contains:  

* The definition of the multimethod of dimension two *m*.
* The definition of the *Operator* classes with concrete classes *Add* and *Multiply*.
* The member *operator* that is the current *Operator* instance, the methods *setAddOperator* and *setMultiplyOperator* to change its value.
* The *fooMatch()* methods for the tuples (*Add*, *A*) and (*Multiply*, *A*).
* The constructor that adds the match methods.
* The *foo(A)* method that is the front method forwarding to the multimethod. The *operator* parameter is hidden to the user of the method.

This class is easily extended to process *B* objects:

```java
public class ExtendedClass2 extends Class2 {
	
	int fooMatch(Add op, B b) {
		return b.a + 3;
	}
	
	int fooMatch(Multiply op, B b) {
		return b.a * 3;
	}
}
```
 
Only *fooMatch()* methods with new tuples (*Add*, *B*) and (*Multiply*, *B*) are added.
We test it:

```java
ExtendedClass2 e2 = new ExtendedClass2();
			
System.out.println("e2.foo on b: " + e2.foo(b));

e2.setMultiplyOperator();

System.out.println("e2.foo on b: " + e2.foo(b));
```

First call to *foo()* is made with the default value of *operator*. Then it is changed by the call to *setMultiplyOperator()*.  

These two examples show how it is easy to use an **inner** multimethod to implement a new dispatch.
This dispatch can be extended making it fully **compatible** with usual Java **inheritance**.
