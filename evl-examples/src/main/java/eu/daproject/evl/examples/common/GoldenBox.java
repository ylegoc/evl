package eu.daproject.evl.examples.common;

public class GoldenBox extends Box {

	public GoldenBox(int size) { super(size); }
	
	@Override
	public String toString() {
		return "GoldenBox " + getSize();
	}
}
