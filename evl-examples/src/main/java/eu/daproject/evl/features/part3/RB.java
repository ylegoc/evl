package eu.daproject.evl.features.part3;

public class RB implements RI {

	private int value;
	
	public RB(int value) {
		this.value = value;
	}

	@Override
	public int get() {
		return value;
	}
}
