package tutorials.tutorial1;

import tutorials.classes.A;
import tutorials.classes.B;
import tutorials.classes.C;
import evl.base.Method1;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		A b = new B(1, 2);
		A c = new C(2, -5);
		
		Foo fooObject = new Foo();
		
		Method1<Integer> foo = Method1.<Integer>builder()
						.build()
						.addAll(Foo.class, "process", fooObject);
		
		System.out.println(foo.invoke(b));
		System.out.println(foo.invoke(c));
	}
}
