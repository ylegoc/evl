package eu.daproject.evl.tomultimethods.part1;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method1;

public class Main {

	public static void print(I obj) {
		
		int res = obj.foo("square", 3);
		System.out.println(obj.getClass().getName() + " -> " + res);
	}
	
	public static void print(Method1<Integer> foo, I obj) throws Throwable {
		
		int res = foo.invoke(obj, "square", 3);
		System.out.println(obj.getClass().getName() + " -> " + res);
	}
	
	public static void main(String[] args) throws Throwable {
		
		I a = new A();
		I b = new B();
		I c = new C();
		
		print(a); // 3
		print(b); // 9
		print(c); // 9
		
		Method1<Integer> foo = new Method1<Integer>().add(new Cases() {
			
			int match(A a, String s, int i) {
				return i;
			}
			
			int match(B b, String s, int i) {
				if (s.equals("square")) {
					return i * i;
				}
				return 0;
			}
		});
		
		print(foo, a); // 3
		print(foo, b); // 9
		print(foo, c); // 9
	}
}
