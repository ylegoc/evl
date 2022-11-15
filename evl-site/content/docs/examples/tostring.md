---
title: "ToString"
description: Another way of definition
weight: 11
---

This example is showing how to use a multimethod to write a more general *toString()* method.

It is based on the [open access](/docs/more-features/open-access.html) feature.
We extend the *toString()* method by adding a first parameter representing the format.
As example of use, we extend some classes defined in the [preliminaries](/docs/examples/preliminaries.html).

### Implementation 

We define a multimethod of dimension two with restrictions on the non-virtual parameters and the return type:

```java
public class ToString {

	private static Method2<String> method = new Method2<String>()
														.returnType(String.class)
														.nonVirtualParameterTypes();
	
	public static Method2<String> method() {
		return method;
	}
	
	static {
		method.add(new Cases() {
			String match(Object format, Object object) {
				return object.toString();
			}
		});
	}
}
```

The *ToString* class is a singleton accessible from anywhere and the less specialized couple (*Object*, *Object*) is defined so that there will always be a matching method for any couple of types. Notice that by not providing arguments to *nonVirtualParameterTypes()*, it means that there is no virtual parameter accepted.

We decide to define the *XML* format. We define it simply as an empty singleton:

```java
public class XML {

	static XML instance = new XML();
	
	static XML getInstance() {
		return instance;
	}
	
	private XML() {
	}
}
```

We can define our first class that use the *ToString* method. We extend the *Car* class:

```java
public class BigCar extends Car {

	public BigCar() {
		super("blue");
	}
	
	static {
		ToString.method().add(new Cases() {
			String match(XML xml, BigCar car) {
				return "<bigCar color=\"" + car.getColor() + "\"/>";
			}
		});
	}
}
```

 We immediately defined the case (*XML*, *BigCar*) for the *ToString* multimethod in the **static block** of the class. 
 Do it here ensures that it is called only once, that would have not been the case if we did it in the constructor.
 Now it very looks like we defined the *toString()* method for *BigCar*.
 On the same principle we extend *Doll*:
 
```java
public class SmallDoll extends Doll {

	private String hair;
	
	public SmallDoll(String hair) {
		super(13);
		this.hair = hair;
	}
	
	static {
		ToString.method().add(new Cases() {
			String match(XML xml, SmallDoll doll) {
				return "<smallDoll height=\"" + doll.getHeight() + "\" hair=\"" + doll.hair + "\"/>";
			}
		});
	}
}
```

The particularity here is we can access the private member *hair* without any getter.
We define a container class by extending *Tallboy*:

```java
public class SmallTallboy extends Tallboy {

	public SmallTallboy() {
		super("wood");
	}
	
	static {
		ToString.method().add(new Cases() {
			String match(XML xml, SmallTallboy tallboy) {
				String result = "<smallTallboy>\n";
				
				for (Element element : tallboy.getElements()) {
					try {
						result += "  " + ToString.method().invoke(xml, element) + "\n";
					} catch (Throwable e) {
						result += "  \n<notToStringable/>\n";
					}
				}
				result += "</smallTallboy>";
				
				return result;
			}
		});
	}
}
```

This time we apply the *ToString* multimethod to all the children by simply calling it: A match method can call recursively the multimethod.
To finish, we observe the results:

```java
System.out.println(ToString.method().invoke(XML.getInstance(), new BigCar()));
System.out.println(ToString.method().invoke(XML.getInstance(), new SmallDoll("blond")));

SmallTallboy tallboy = new SmallTallboy();

tallboy.add(new BigCar());
tallboy.add(new SmallDoll("brown"));

System.out.println(ToString.method().invoke(XML.getInstance(), tallboy));
```

We presented a way to define the *toString()* method using a multimethod. This approach offers interesting features:

* We can completely redefine our own *toString()* method: for classes inherited from a library, it is not possible to override their *toString()* method 
but we can add a match method for *ToString*.
* We can add as many formats as we want e.g. add the JSON format.

The approach based on an **open access** can be done for any other method. The singleton makes it very accessible and does not require to copy a reference. 