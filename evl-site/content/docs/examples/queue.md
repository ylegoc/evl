---
title: "Queue"
description: Producer and consumer
weight: 10
---

This example is showing how to use a multimethod in the context of a queue of objects.

We use instead the classes defined in the [preliminaries](/docs/examples/preliminaries.html).
We present some simple producer and consumer that share a queue of *Element* objects.  
The idea is to count the different types of objects. The producer creates randomly some *Element* objects i.e. *Car*, *Game*, *Tallboy*, etc.
The consumer counts the different types of objects.

### Implementation 

We first implement a toy counter to memorize the different counters. We only process *Toy* objects here.
We do not define the multimethod but the methods that will be dispatched: 

```java
public class ToyCounter {

	private int carCount;
	private int dollCount;
	private int gameCount;

	void reset() {
		carCount = 0;
		dollCount = 0;
		gameCount = 0;
	}
	
	public void count(Car car) {
		carCount++;
	}
	
	public void count(Doll doll) {
		dollCount++;
	}
	
	public void count(Game game) {
		gameCount++;
	}

	public int getCarCount() {
		return carCount;
	}

	public int getDollCount() {
		return dollCount;
	}

	public int getGameCount() {
		return gameCount;
	}
}
```

The class *ToyCounter* has three counters that are incremented each time a certain type of object is received.

Now we can define the producer and the consumer. We need a random generator and the multimethod that will dispatch to the *ToyCounter* object.
We also want to count the lost elements i.e. the ones that were not processed. We define the class *ProducerConsumer*:

```java
public class ProducerConsumer {

	private Random random = new Random();
	private int lostElements;
	
	private Element createElement() {
		int type = random.nextInt(6);
		switch (type) {
		case 0:
			return new Car("blue");
		case 1:
			return new Doll(18);
		case 2:
			return new Game(5);
		case 3:
			return new Box(10);
		case 4:
			return new Room("white");
		case 5:
			return new Tallboy("wood");
		}
		
		return null;
	}
	
	public void run(Method1<Void> counterMethod) {
		
		LinkedBlockingQueue<Element> queue = new LinkedBlockingQueue<Element>();
		
		Thread producer = new Thread(() -> {
			
			for (int i = 0; i < 50; ++i) {
				try {
					queue.put(createElement());
				}
				catch (InterruptedException e) {
				}
			}
		});
		
		Thread consumer = new Thread(() -> {
		    
			for (int i = 0; i < 50; ++i) {
				Element element = null;
				try {
					element = queue.take();
				}
				catch (InterruptedException e) {
				}
				
				try {
					counterMethod.invoke(element);
				}
				catch (Throwable e) {
					lostElements++;
					System.err.println("Error, " + element + " cannot be counted.");
				}
			}
		});
		
		lostElements = 0;
		
		producer.start();
		consumer.start();
		
		try {
			producer.join();
			consumer.join();
		}
		catch (InterruptedException e) {
		}
	}
	
	public int getLostElements() {
		return lostElements;
	}
}
```

The *createElement()* methods creates randomly a *Element* object and the *run()* method runs the producer and the consumer in two threads.
It takes a multimethod as argument so that it remains reusable. The class can then be used in a program:

```java
ProducerConsumer runner = new ProducerConsumer();

Method1<Void> counterMethod = new Method1<Void>();

ToyCounter toyCounter = new ToyCounter();
counterMethod.add(toyCounter, "count");

System.out.println("Run the counter...");
runner.run(counterMethod);

System.out.println(toyCounter.getCarCount() + " cars received.");
System.out.println(toyCounter.getDollCount() + " dolls received.");
System.out.println(toyCounter.getGameCount() + " games received.");
System.out.println(runner.getLostElements() + " elements lost.\n");
```

We create a new multimethod *counterMethod* for which we add the *count()* methods of the *ToyCounter* object.
It is passed to the *run()* method. We get some cars, dolls and games but we also lose some elements. 
Indeed only *Toy* objects are counted, the *Container* objects have been forgotten.

### Extend the process

We simply define a *ContainerCounter* class like the *ToyCounter* class:

```java
public class ContainerCounter {

	private int boxCount;
	private int roomCount;
	private int tallboyCount;
	
	void reset() {
		boxCount = 0;
		roomCount = 0;
		tallboyCount = 0;
	}
	
	public void count(Box box) {
		boxCount++;
	}
	
	public void count(Room room) {
		roomCount++;
	}
	
	public void count(Tallboy tallboy) {
		tallboyCount++;
	}

	public int getBoxCount() {
		return boxCount;
	}

	public int getRoomCount() {
		return roomCount;
	}

	public int getTallboyCount() {
		return tallboyCount;
	}
}
```

We can process every type of *Elements*:

```java
ContainerCounter containerCounter = new ContainerCounter();
counterMethod.add(containerCounter, "count");

System.out.println("Run the counter...");
runner.run(counterMethod);

System.out.println(toyCounter.getCarCount() + " cars received.");
System.out.println(toyCounter.getDollCount() + " dolls received.");
System.out.println(toyCounter.getGameCount() + " games received.");
System.out.println(containerCounter.getBoxCount() + " boxes received.");
System.out.println(containerCounter.getRoomCount() + " rooms received.");
System.out.println(containerCounter.getTallboyCount() + " tallboys received.");
System.out.println(runner.getLostElements() + " elements lost.");
```

And now no element is lost.

To finish, this example shows how to pass **stateful objects** to a multimethod, that is necessary when we need to memorize some values.
Moreover the example shows that when the dispatch is external to a class hierarchy, it is **more natural** to use a multimethod than using the *instanceof* operator.
We also showed how to extend the process by adding a new counter.

This example shows a multimethod of dimension one but we could add another dimension e.g. the weather and count the couples element and weather.   
 

