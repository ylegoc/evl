package tutorials.tutorial1b;

import tutorials.classes.B;
import tutorials.classes.C;
import evl.base.Method1;
import evl.exceptions.BadNonVirtualParameterTypesException;
import evl.exceptions.BadNumberOfVirtualParameterTypesException;
import evl.exceptions.InvocationException;

public class Foo {
	
	private Method1<Integer> process;
	
	public Foo() {
		 try {
			process = new Method1<Integer>().addAll(Foo.class, "process", this);
		} catch (BadNumberOfVirtualParameterTypesException | BadNonVirtualParameterTypesException e) {
		}
	}

	public int process(Object obj) throws InvocationException {
		return process.invoke(obj);
	}
	
	public int process(B obj) {
		return 1 + obj.getB();
	}

	public int process(C obj) {
		return 2 + obj.getC();
	}
}
