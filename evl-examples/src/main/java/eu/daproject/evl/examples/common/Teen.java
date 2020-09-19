package eu.daproject.evl.examples.common;

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