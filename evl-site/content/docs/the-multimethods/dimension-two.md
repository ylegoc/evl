---
title: "In two dimensions"
description: Jump to another dimension
weight: 2
---

In the last section we defined a multimethod that was providing the same dispatch as a simple class hierarchy with overriden methods.
What is new is that you can define it outside the class hierarchy and have as many multimethods as you want.

In this section, we show how to define a multimethod of dimension two i.e. having a dispatch based on two parameters.
Let's modify the classes *A* and *B* by adding them a member:

```java
public interface I {

}

public class A implements I {

	public int id = 5;
}

public class B implements I {

	public float fd = 7.3f;
}

public class C extends B {

}
```

There is no method is in the hierarchy but only data with the *int* and *float* members.

Let's define a multimethod of dimension two:

```java
Method2<Float> foo2 = new Method2<Float>().add(new Cases() {
			
	float match(A x, A y, String s) {
		return x.id;
	}
	
	float match(A x, B y, String s) {
		if (s.equals("multiply")) {
			return x.id * y.fd;
		}
		return 0;
	}
});
```

This time, we define a *Method2&lt;Float&gt;* because we want to have two virtual parameters and return a *float* value.
We define two *match()* methods for the couple of types (*A*, *A*) and (*A*, *B*). We add a third non-virtual *String* parameter.
To test it, we define another *print()* method:

```java
public static void print(Method2<Float> foo2, I obj1, I obj2) throws Throwable {
		
	float res = foo2.invoke(obj1, obj2, "multiply");
	System.out.println(obj1.getClass().getName() + ", " + obj2.getClass().getName() + " -> " + res);
}
```

Let's observe what happens for the following arguments:

```java
I a = new A();
I b = new B();
I c = new C();

try {
	print(foo2, a, a); // 5
	print(foo2, a, b); // 36.5
	print(foo2, a, c); // 36.5
	print(foo2, b, c); // error
}
catch (Throwable e) {
	e.printStackTrace();
} 
```

First call is made on the couple of objects of dynamic type (*A*, *A*) and the called method is naturally *match(A, A, String)*.  
Second call is made on the couple of objects of dynamic type (*A*, *B*) and the called method is naturally *match(A, B, String)*.  
For these two calls, there is a *match()* method that **perfect matches** the dynamic types.  
That is not the case for the third call where there is no *match()* method defined for (*A*, *C*). However the *match(A, B, String)* 
method is compatible whereas *match(A, A, String)* is not, that is why it is called.  
Fourth call is made on the dynamic types (*B*, *C*) for which there is **no compatible** *match()* method. The call fails and an exception is thrown.

It is easy to define a multimethod of dimension two however the *invoke()* can fail if there is no compatible *match()* method for the arguments.