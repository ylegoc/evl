package eu.daproject.evl.features.part6;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method1;

public class Main {
	
	public static void main(String[] args) {
		
		Object a = new A();
		Object b = new B();
		
		Method1<Void> method = new Method1<Void>().add(new Cases() {
			
			void match(A a) {
				System.out.println("I am A");
			}
			
			void match(B b) {
				System.out.println("I am B");
			}
			
		});
		
		try {
			method.invoke(a);
			method.invoke(b);
		}
		catch (Throwable e) {
			System.err.println(e);
		}
		
		method.add(new Cases() {
			
			void match(A a) {
				System.out.println("I really am A");
			}
		});
		
		try {
			method.invoke(a);
			method.invoke(b);
		}
		catch (Throwable e) {
			System.err.println(e);
		}
	}
}
