package eu.daproject.evl.examples.common;

public class Doll implements Toy {

	private int height;
	public Doll(int height) { this.height = height; }
	public int getHeight() { return height; }
	
	@Override
	public String toString() {
		return "Doll " + height;
	}
}
