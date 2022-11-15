---
title: "Preliminaries"
description: Common classes
weight: 1
---

Before presenting a set of examples, some common classes have to be defined.
The next examples will use these classes.

We model a child room where some toys (*Car*, *Doll*, *Game*) are in containers (*Box*, *Tallboy*, *Room*).
We voluntarily never define interface methods because multimethods are a replacement to common *virtual* methods.

Let's define the classes:

```java

public interface Element {}

public interface Toy extends Element {}

public class Car implements Toy {

	private String color;
	public Car(String color) { this.color = color; }
	public String getColor() { return color; }
	
	@Override
	public String toString() {
		return "Car " + color;
	}
}

public class Doll implements Toy {

	private int height;
	public Doll(int height) { this.height = height; }
	public int getHeight() { return height; }
	
	@Override
	public String toString() {
		return "Doll " + height;
	}
}

public class Game implements Toy {

	private int players;
	public Game(int players) { this.players = players; }
	public int getPlayers() { return players; }
	
	@Override
	public String toString() {
		return "Game " + players;
	}
}

public class Container implements Element {
	
	private List<Element> elements = new ArrayList<Element>();
	public void add(Element element) { elements.add(element); }
	public List<Element> getElements() { return elements; }
}

public class Box extends Container {

	private int size;
	public Box(int size) { this.size = size; }
	public int getSize() { return size; }
	
	@Override
	public String toString() {
		return "Box " + size;
	}
}

public class GoldenBox extends Box {

	public GoldenBox(int size) { super(size); }
	
	@Override
	public String toString() {
		return "GoldenBox " + getSize();
	}
}

public class Tallboy extends Container {

	private String material;
	public Tallboy(String material) { this.material = material; }
	public String getMaterial() { return material; }
	
	@Override
	public String toString() {
		return "Tallboy " + material;
	}
}

public class Room extends Container {

	private int color;
	public Room(int color) { this.color = color; }
	public int getColor() { return color; }
	
	@Override
	public String toString() {
		return "Room " + color;
	}
}
```

We provide a class to build an example of tree:

```java

public class RoomBuilder {

	public static Room build() {
		
		Box box1 = new Box(100);
		box1.add(new Car("red"));
		box1.add(new Car("black"));
		
		Box box2 = new Box(200);
		box2.add(new Doll(10));
		box2.add(new Doll(20));
		
		Tallboy tallboy = new Tallboy("mat");
		tallboy.add(box1);
		tallboy.add(box2);
		tallboy.add(new Game(3));

		Box box3 = new Box(100);
		box3.add(new Car("blue"));
		box3.add(new Car("orange"));
		
		Room room = new Room(4);
		room.add(tallboy);
		room.add(box3);
		
		return room;
	}
}
```

We also define some classes related to the age of a person:

```java
public interface Person {}

public class Kid implements Person {
	private int age;
	public Kid(int age) { this.age = age; }
	public int getAge() { return age; };
	
	@Override
	public String toString() {
		return "Kid " + age;
	}
}

public class Teen implements Person {
	public enum Sex {BOY, GIRL};
	private Sex sex;
	public Teen(Sex sex) { this.sex = sex; }
	public Sex getSex() { return sex; };
	
	@Override
	public String toString() {
		return "Teen " + sex;
	}
}

public class Adult implements Person {

	@Override
	public String toString() {
		return "Adult";
	}
}
```

We define some classes related to the weather:

```java
public interface Weather {}

public class Sunny implements Weather {

	@Override
	public String toString() {
		return "Sunny";
	}
}

public class Rainy implements Weather {

	@Override
	public String toString() {
		return "Rainy";
	}
}
```

We define some classes related to a location:

```java
public interface Location {}

public class Inside implements Location {

	@Override
	public String toString() {
		return "Inside";
	}
}

public class Outside implements Location {
	
	@Override
	public String toString() {
		return "Outside";
	}
}

public class Somewhere implements Location {
	
	@Override
	public String toString() {
		return "Somewhere";
	}
}
```
