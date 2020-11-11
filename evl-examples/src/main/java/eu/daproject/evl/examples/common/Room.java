package eu.daproject.evl.examples.common;

public class Room extends Container {

	private String color;
	public Room(String color) { this.color = color; }
	public String getColor() { return color; }
	
	@Override
	public String toString() {
		return "Room " + color;
	}
}
