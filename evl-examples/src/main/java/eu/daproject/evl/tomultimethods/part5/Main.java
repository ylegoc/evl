package eu.daproject.evl.tomultimethods.part5;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method2;
import eu.daproject.evl.Priority;
import eu.daproject.evl.SymmetricComparator;

public class Main {

	public static void print(Method2<Float> foo2, I obj1, I obj2) throws Throwable {
		
		float res = foo2.invoke(obj1, obj2, "add");
		System.out.println(obj1.getClass().getName() + ", " + obj2.getClass().getName() + " -> " + res);
	}
	
	public static void main(String[] args) {
		
		I a = new A();
		I b = new B();
		I c = new C();
		I d = new D();
		
		// first part
		{
			Method2<Float> foo2 = new Method2<Float>()
					.comparator(new SymmetricComparator())
					.add(new Cases() {
				
				float match(A x, A y, String s) {
					return x.id;
				}
				
				float match(A x, B y, String s) {
					if (s.equals("multiply")) {
						return x.id * y.fd;
					}
					return 0;
				}
				
				float match(A x, C y, String s) {
					if (s.equals("add")) {
						return x.id + y.fd;
					}
					return 0;
				}
				
				float match(D x, B y, String s) {
					if (s.equals("add")) {
						return 2 * x.id + 3 * y.fd;
					}
					return 0;
				}
				
				float match(D x, C y, String s) {
					return match((A)x, y, s);
				}
			});
			
			try {
				print(foo2, a, c); // 12.3
				print(foo2, d, b); // 31.9
				print(foo2, d, c); // 12.3
			}
			catch (Throwable e) {
				e.printStackTrace();
			}
		}
		
		// second part
		{
			Method2<Float> foo2 = new Method2<Float>()
					.comparator(new SymmetricComparator())
					.add(new Cases() {
				
				float match(A x, A y, String s) {
					return x.id;
				}
				
				float match(A x, B y, String s) {
					if (s.equals("multiply")) {
						return x.id * y.fd;
					}
					return 0;
				}
				
				float match(A x, C y, String s) {
					if (s.equals("add")) {
						return x.id + y.fd;
					}
					return 0;
				}
				
				float match(D x, B y, String s) {
					if (s.equals("add")) {
						return 2 * x.id + 3 * y.fd;
					}
					return 0;
				}
				
			});
			
			try {
				foo2.setData(Priority.valueOf(1), D.class, B.class);
						
				print(foo2, a, c); // 12.3
				print(foo2, d, b); // 31.9
				print(foo2, d, c); // 31.9
				
				foo2.setData(Priority.valueOf(-1), D.class, B.class);
				
				print(foo2, a, c); // 12.3
				print(foo2, d, b); // 31.9
				print(foo2, d, c); // 12.3
			}
			catch (Throwable e) {
				e.printStackTrace();
			}
		}
		
	}
}
