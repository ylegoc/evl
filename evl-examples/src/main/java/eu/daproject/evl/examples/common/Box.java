package eu.daproject.evl.examples.common;

public class Box extends Container {

	private int size;
	public Box(int size) { this.size = size; }
	public int getSize() { return size; }
	
	@Override
	public String toString() {
		return "Box " + size;
	}
}
