package eu.daproject.evl.features.part5;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method2;

public class Class2 {

	protected static Method2<Integer> foo = new Method2<Integer>();
	
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
	
	public Class2() {
		
		foo.add(new Cases() {
			
			int match(Add op, A a) {
				return a.a + 2;
			}
			
			int match(Multiply op, A a) {
				return a.a * 2;
			}
		});
	}
	
	public int foo(A a) {
		
		try {
			return foo.invoke(operator, a);
		}
		catch (Throwable e) {
			// Cannot happen.
			return 0;
		}
	}
}
