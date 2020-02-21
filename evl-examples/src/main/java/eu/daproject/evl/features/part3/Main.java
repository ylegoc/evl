package eu.daproject.evl.features.part3;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method1;

public class Main {
	
	public static void main(String[] args) {
		
		I a = new A();
		I b = new B();
				
		Method1<RI> method = new Method1<RI>()
				.returnType(RI.class)
				.add(new Cases() {
			
			RA match(A a) {
				return new RA();  
			}
			
			RB match(B b) {
				return new RB(b.id);  
			}
			
		});
		
		try {
			System.out.println("A: " + method.invoke(a).get());
			System.out.println("B: " + method.invoke(b).get());
		}
		catch (Throwable e) {
			System.err.println(e);
		}
		
		
		Method1<Integer> print = new Method1<Integer>()
				.add(new Cases() {
			
			int match(RA a) {
				return 3;  
			}
			
			int match(RB b) {
				return b.value;  
			}
			
		});
		
		try {
			System.out.println("A: " + print.invoke(method.invoke(a)));
			System.out.println("B: " + print.invoke(method.invoke(b)));
		}
		catch (Throwable e) {
			System.err.println(e);
		}
		
		Method1<RI> method2 = new Method1<RI>()
				.returnType(RI.class)
				.nonVirtualParameterTypes(int.class, String.class);
		
		method2.add(new Cases() {
			
			RA match(A a, int i, String name) {
				return new RA();
			}
			
			RB match(B b, int i, String name) {
				return new RB(b.id + i + name.length());  
			}
			
		});
	}
}
