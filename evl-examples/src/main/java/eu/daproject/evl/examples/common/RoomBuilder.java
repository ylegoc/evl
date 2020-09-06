package eu.daproject.evl.examples.common;

public class RoomBuilder {

	public static Room build() {
		
		Box box1 = new Box(100);
		box1.add(new Car(1));
		box1.add(new Car(2));
		
		Box box2 = new Box(200);
		box2.add(new Doll(10));
		box2.add(new Doll(20));
		
		Tallboy tallboy = new Tallboy("mat");
		tallboy.add(box1);
		tallboy.add(box2);
		tallboy.add(new Game(3));

		Box box3 = new Box(100);
		box3.add(new Car(3));
		box3.add(new Car(4));
		
		Room room = new Room(4);
		room.add(tallboy);
		room.add(box3);
		
		return room;
	}
}
