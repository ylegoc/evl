package eu.daproject.evl.tomultimethods.part6;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method4;

public class Main {

	public static void print(Method4<String> foo4, I obj1, I obj2, I obj3, I obj4) throws Throwable {
		
		String res = foo4.invoke(obj1, obj2, obj3, obj4);
		System.out.println(obj1.getClass().getName() + ", " + obj2.getClass().getName() + ", " + obj3.getClass().getName() + ", " + obj4.getClass().getName() + " -> " + res);
	}
	
	public static void main(String[] args) {
		
		I a = new A();
		I b = new B();
		I c = new C();
		I d = new D();
		
		// first part
		{
			Method4<String> foo4 = new Method4<String>().add(new Cases() {
				
				String match(I x, I y, I z, I t) {
					return "default";
				}
				
				String match(A x, A y, A z, A t) {
					return "A, A, A, A = " + x.id + ", " + y.id + ", " + z.id + ", " + t.id;
				}
				
				String match(A x, B y, C z, D t) {
					return "A, B, C, D = " + x.id + ", " + y.fd + ", " + z.fd + ", " + t.id;
				}
			});
			
			try {
				print(foo4, a, a, a, a); // A, A, A, A... 
				print(foo4, d, a, c, b); // default
				print(foo4, a, b, c, d); // A, B, C, D...
			}
			catch (Throwable e) {
				e.printStackTrace();
			}
		}
		
	}
}
