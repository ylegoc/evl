package eu.daproject.evl.tomultimethods.part2;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method2;

public class Main {

	public static void print(Method2<Float> foo2, I obj1, I obj2) throws Throwable {
		
		float res = foo2.invoke(obj1, obj2, "multiply");
		System.out.println(obj1.getClass().getName() + ", " + obj2.getClass().getName() + " -> " + res);
	}
	
	public static void main(String[] args) {
		
		I a = new A();
		I b = new B();
		I c = new C();
		
		Method2<Float> foo2 = new Method2<Float>().add(new Cases() {
			
			float match(A x, A y, String s) {
				return x.id;
			}
			
			float match(A x, B y, String s) {
				if (s.equals("multiply")) {
					return x.id * y.fd;
				}
				return 0.0f;
			}
		});
		
		try {
			print(foo2, a, a); // 5
			print(foo2, a, b); // 36.5
			print(foo2, a, c); // 36.5
			print(foo2, b, c); // error
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
