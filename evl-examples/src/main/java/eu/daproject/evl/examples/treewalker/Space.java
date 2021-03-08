package eu.daproject.evl.examples.treewalker;

public class Space {
	
	String space = "";
	
	public void increment() {
		space += "  ";
	}
	
	public void decrement() {
		space = space.substring(2);
	}
	
	public String get() {
		return space;
	}
}
