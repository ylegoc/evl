package eu.daproject.evl.examples.common;

public class Car implements Toy {

	private String color;
	public Car(String color) { this.color = color; }
	public String getColor() { return color; }
	
	@Override
	public String toString() {
		return "Car " + color;
	}
}
