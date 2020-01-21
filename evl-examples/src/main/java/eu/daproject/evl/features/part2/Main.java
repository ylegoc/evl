package eu.daproject.evl.features.part2;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method1;

public class Main {
	
	public static void main(String[] args) {
		
		Object a = new A();
		Object b = new B();
		Object c = new C();
		
		Method1<Integer> method = new Method1<Integer>().add(new Cases() {
			
			int match(IA a) {
				return a.getA();
			}
			
			int match(IB b) {
				return (int)b.getB();
			}
			
		});
		
		try {
			System.out.println("A: " + method.invoke(a));
			System.out.println("B: " + method.invoke(b));
			System.out.println("C: " + method.invoke(c));
		}
		catch (Throwable e) {
			System.err.println(e);
		}
		
		method.add(new Cases() {
			
			int match(C c) {
				return c.getA() + (int)c.getB();
			}
		});
		
		try {
			System.out.println("C: " + method.invoke(c));
		}
		catch (Throwable e) {
			System.err.println(e);
		}
	}
}
