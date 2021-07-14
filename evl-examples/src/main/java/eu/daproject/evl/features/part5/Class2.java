package eu.daproject.evl.features.part5;

import eu.daproject.evl.Method2;

public class Class2 {

	protected Method2<Integer> m = new Method2<Integer>();
	
	protected static class Operator {};
	protected static class Add extends Operator {};
	protected static class Multiply extends Operator {};
	
	protected Operator operator = new Add();
	
	protected void setAddOperator() {
		operator = new Add();
	}
	
	protected void setMultiplyOperator() {
		operator = new Multiply();
	}
	
	protected int fooMatch(Add op, A a) {
		return a.a + 2;
	}
	
	protected int fooMatch(Multiply op, A a) {
		return a.a * 2;
	}
	
	public Class2() {
		m.add(this, "fooMatch");
	}
	
	public int foo(A a) throws Throwable {
		return m.invoke(operator, a);
	}
}
