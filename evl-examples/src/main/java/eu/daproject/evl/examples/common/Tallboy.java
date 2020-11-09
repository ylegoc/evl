package eu.daproject.evl.examples.common;

public class Tallboy extends Container {

	private String material;
	public Tallboy(String material) { this.material = material; }
	public String getMaterial() { return material; }
	
	@Override
	public String toString() {
		return "Tallboy " + material;
	}
}
