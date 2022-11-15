---
title: "Comparator"
description: Compare the elements
weight: 6
---

In this example, we present a concrete **comparator** between objects using a multimethod.
We demonstrate that it is a good tool to design such functionality. We also show how it is easy to extend it to a further dimension to refine the behavior.

We define an *interest* comparator, which is subjective by nature, on two elements of the room (*Car*, *Tallboy*, etc.) for a particular person (*Kid*, *Adult*, etc.).   
The comparator returns 1 if the first element is more interesting than the second one, -1 if the first element is less interesting than the second one 
and 0 if they are as interesting. 

The classes used in the example are defined in the [preliminaries](/docs/examples/preliminaries.html).

### Specific implementation for a Kid 

We do not show immediately the general case. So we first start by defining the interest comparator for a *Kid*.  
The rules are simple: the *Kid* prefers the *Car* in any case. Other elements have the same interest for him.
Here is the implementation of the multimethod:

```java
Method2<Integer> interestComparator = new Method2<Integer>()
						.symmetricComparator()
						.add(new Cases() {
			
	int match(Car car, Element element) {
		return 1;
	}
	
	int match(Element element, Car car) {
		return -1;
	}
	
	int match(Element element1, Element element2) {
		return 0;
	}
});
```

To facilitate the test, we define a *test* method:

```java
void test(Method2<Integer> comparator, Element element1, Element element2) throws Throwable {
		
	int result = comparator.invoke(element1, element2);
	
	if (result == 1) {
		System.out.println(element1 + " is more interesting than " + element2); 
	}
	else if (result == -1) {
		System.out.println(element1 + " is less interesting than " + element2); 
	}
	else {
		System.out.println(element1 + " is as interesting as " + element2);
	}
}
```

But what happens on the (*Car*, *Car*) couple?

```java
test(interestComparator, new Car("blue"), new Car("red"));
```

The invocation fails by an ambiguity exception. Indeed, the multimethod is not using the default asymmetric method comparator i.e. the lexicographic comparator 
but the symmetric method comparator i.e. the product comparator. The product comparator is more "democratic" but it also leads to more ambiguities.

In our case, the match methods (*Car*, *Element*), (*Element*, *Car*) and (*Element*, *Element*) are all compatible but (*Car*, *Element*) 
and (*Element*, *Car*) are more specific and it is impossible to choose between the twos.
 
In fact, we made a mistake, we forgot to define the case (*Car*, *Car*) for which the *Kid* will eventually prefer one *Car* for an obscure reason:

```java
interestComparator.add(new Cases() {
			
	int match(Car car1, Car car2) {
		return Integer.compare(car1.getColor().length(), car2.getColor().length());
	}
});
```

So we are ready to check a bunch of elements:

```java
Element[] elements = new Element[] {new Car("blue"), new Car("red"), new Tallboy("wood"), new Box(12)};

for (Element element1 : elements) {
	for (Element element2 : elements) {
		test(interestComparator, element1, element2);				
	}
}
```

And now what about adding a special case. The *Kid* secretly prefers a special *GoldenBox* but now we know it and we can add the rule to our comparator:

```java
interestComparator.add(new Cases() {
				
	int match(GoldenBox box, Element element) {
		return 1;
	}
	
	int match(Element element, GoldenBox box) {
		return -1;
	}
	
	int match(GoldenBox box1, GoldenBox box2) {
		return Integer.compare(box1.getSize(), box2.getSize());
	}
});
```

Like the *Car* we add three match methods for (*GoldenBox*, *Element*), (*Element*, *GoldenBox*) and (*Element*, *Element*). 
But once again the symmetric operator tricked us. 
Another ambiguity appeared: for the couple (*Car*, *GoldenBox*) it is not possible to choose between (*Car*, *Element*) and (*Element*, *GoldenBox*).
Let's add the match methods:

```java
interestComparator.add(new Cases() {
				
	int match(GoldenBox box, Car car) {
		return 1;
	}
	
	int match(Car car, GoldenBox box) {
		return -1;
	}
});
```

And we can check the elements with total peace of mind:

```java
Element[] elements = new Element[] {new Car("blue"), new Car("red"), new Tallboy("wood"), new Box(12), new GoldenBox(15)};
				
for (Element element1 : elements) {
	for (Element element2 : elements) {
		test(interestComparator, element1, element2);				
	}
}   
```

This specific example of **binary** comparator showed us to take care of using the symmetric method comparator (do not make a confusion between the interest comparator 
and the method comparator used internally to select a match method).  

For more details on the differences between the asymmetric and the symmetric method comparators, see the pages [Change comparator](/docs/the-multimethods/change-comparator.html) 
and [asymmetric vs symmetric](docs/theory/asymmetric-vs-symmetric.html).

We are now ready to define the comparator in a general case: the comparator can also apply to an *Adult*.

### General implementation

Now we want that the comparator is also able to apply to an *Adult* who will have his own preferences. 

The solution is to extend the multimethod of dimension two to a third dimension that will represent the *Person*. 
We decide to add on the first dimension. But the dimension representing the *Person* has a different meaning than the two others.
It is not part of the comparator but rather define a *family* of comparators.
For that reason we decide not to use the symmetric operator for the three dimensions i.e. only for the two dimensions used for comparison.
The EVL library does not provide a hybrid method comparator that would mix a lexicographic and a product comparator.
No problem, we will write it.

We define the *PersonAndElementsComparator* by extending the *MethodComparator* class:

```java
public class PersonAndElementsComparator extends MethodComparator {

	protected int[] getLastDimensions(int[] d) {
		
		int[] result = new int[d.length - 1];
		
		for (int i = 0; i < d.length - 1; ++i) {
			result[i] = d[i + 1];
		}
		
		return result;
	}
	
	protected int compareTuples(int[] d1, int[] d2) {

		// Integer comparison on the first dimension.
		int firstComparison = Integer.compare(d1[0], d2[0]);
		if (firstComparison != 0) {
			return firstComparison;	
		}
		
		// If equality on the first dimension, then product comparison on the last dimensions.
		return ProductDistanceComparator.compare(getLastDimensions(d1), getLastDimensions(d2));
	}
	
	@Override
	public int compare(MethodItem m1, MethodItem m2) {
		return super.compareWithPriority(m1, m2, compareTuples(m1.getDistanceTuple(), m2.getDistanceTuple()));
	}
}
```

The method that we need to redefine is *compare(MethodItem m1, MethodItem m2)*. We do it with the help of *compareWithPriority* which is defined into *MethodComparator*
and avoids us to manage priorities. See [ambiguity resolution](/docs/the-multimethods/ambiguity-resolution.html) for an example of use of priorities.
However we need to define the distance tuple comparator. For that we separate the tuples from the first dimension which we simply compare and apply the lexicographic comparator
for the last dimensions.

We can define our multimethod of dimension three by getting the rules defined for the *Kid* specific comparator and adding the ones we emitted for the *Adult*: 
    
```java
Method3<Integer> interestComparator = new Method3<Integer>()
							.comparator(new PersonAndElementsComparator())
							.add(new Cases() {
	
	int match(Kid kid, Car car, Element element) {
		return 1;
	}
	
	int match(Kid kid, Element element, Car car) {
		return -1;
	}
	
	int match(Kid kid, Element element1, Element element2) {
		return 0;
	}
	
	int match(Kid kid, Car car1, Car car2) {
		return Integer.compare(car1.getColor().length(), car2.getColor().length());
	}
	
	int match(Kid kid, GoldenBox box, Element element) {
		return 1;
	}
	
	int match(Kid kid, Element element, GoldenBox box) {
		return -1;
	}
	
	int match(Kid kid, GoldenBox box1, GoldenBox box2) {
		return Integer.compare(box1.getSize(), box2.getSize());
	}
		
	int match(Kid kid, GoldenBox box, Car car) {
		return 1;
	}
	
	int match(Kid kid, Car car, GoldenBox box) {
		return -1;
	}
	
	int match(Adult adult, Container container, Toy toy) {
		return 1;
	}
	
	int match(Adult adult, Toy toy, Container container) {
		return -1;
	}
	
	int match(Adult adult, Toy toy1, Toy toy2) {
		return 0;
	}
	
	int match(Adult adult, Container container1, Container container2) {
		return 0;
	}
});
```

This time, there is no ambiguity to resolve, we can check all the elements:

```java
Kid kid = new Kid(5);
Adult adult = new Adult();

Element[] elements = new Element[] {new Car("blue"), new Car("red"), new Tallboy("wood"), new Box(12), new GoldenBox(15)};

for (Element element1 : elements) {
	for (Element element2 : elements) {
		test(interestComparator, kid, element1, element2);				
	}
}

for (Element element1 : elements) {
	for (Element element2 : elements) {
		test(interestComparator, adult, element1, element2);				
	}
}
```

This example demonstrates the power of multimethods and their ability to adapt to *problem*. Once again extension is easy with EVL multimethods.
So you can now define the rules that would apply for a *Teen*.
You can also extend the comparator to another dimension that could be the *Weather*. Indeed, maybe our *Kid* and *Adult* have different preferences for a rainy weather than for a sunny weather? 

To conclude, multimethods are ideal to process class sets. They help to categorize classes and offer a more set-centered way of thinking. They also force to think about explicit dispatch rules.


