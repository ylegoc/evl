---
title: "Open access"
description: Everyone can use it
weight: 5
---

As EVL multimethods are objects, they can be either **global** i.e. accessible anywhere or **local** and protected to a class hierarchy. It will depend of course on what you want to do with your multimethod. We present the **open** access here where a multimethod can be invoked and extended anywhere. 

Let's illustrate this by a simple: we want to define a general *print* method that returns a String.

Let's start by the simple class hierarchy as in previous page:

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

We define a *Print* class in which we define a static method *method()* returning the multimethod:

```java
public class Print {

	private static Method1<String> method = new Method1<String>();
	
	public static Method1<String> method() {
		return method;
	}
	
}
```

We could have named the method accessing the singleton *getInstance()* as it is often the case in Java, however we think that a multimethod is a special class that can have a special accessor name. The advantage with *m* is that when reading, the eye is focused on *Print*. Moreover it can clearly identify that this is a global multimethod.

Let's feed our multimethod. This can now be done anywhere:

```java
Print.method().add(new Cases() {
			
	String match(A a) {
		return "{ A a:" + a.a + " }";
	}
	
	String match(B b) {
		return "{ B a:" + b.a + " }";
	}
});
```

And now let's apply it to some objects:

```java
A a = new A();
B b = new B(3);

System.out.println(Print.method().invoke(a));
System.out.println(Print.method().invoke(b));
```

Everything is fine and now we define another class somewhere else:

```java
public class C {
	
	public int c;
	public A a;
	
	public C(int c, A a) {
		this.c = c;
		this.a = a;
	}
}
```

The particularity of *C* is that it has a member of type *A*. So we can combine the print methods and extend the *Print* multimethod to *C*:

```java
Print.method().add(new Cases() {
	
	String match(C c) throws Throwable {
		return "{ C c:" + c.c + " a:" + Print.method().invoke(c.a) + " }";
	}
});
```

We need to call *Print.method().invoke()* on the *a* member and add *Throwable* in the throwable exceptions. We also could have caught the exception if this is a possible case.
Now we can apply it to a *C* object:

```java
C c = new C(5, b);

System.out.println(Print.method().invoke(c));
```

We showed how to write **global** multimethods. That can be useful when the multimethod is general and can interest developers of different packages.  
However, it also means that another code can extend the multimethod.
