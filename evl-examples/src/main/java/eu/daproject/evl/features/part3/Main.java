package eu.daproject.evl.features.part3;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method1;

public class Main {
	
	public static void main(String[] args) {
		
		I a = new A();
		I b = new B();
				
		Method1<RI> method = new Method1<RI>()
				//.returnType(RI.class)
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
		
	}
}
