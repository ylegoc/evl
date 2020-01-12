package eu.daproject.evl.features.part1;

import eu.daproject.evl.Method1;

public class Main {
	
	public static class CounterA {

		public int count = 0;
		
		public void match(A obj) {
			count += 1;
		}

		public void match(D obj) {
			count += 2;
		}
	}
	
	public static class CounterB {

		public int count = 0;
		
		public void match(B obj) {
			count += 10;
		}

		public void match(C obj) {
			count += 11;
		}
	}
	
	public static void main(String[] args) {
		
		I a = new A();
		I b = new B();
		I c = new C();
		I d = new D();
		
		CounterA counterA = new CounterA();
		
		Method1<Void> method = new Method1<Void>().add(counterA);
		
		try {
			method.invoke(a);
			method.invoke(d);
			
			System.out.println("Counter A: " + counterA.count);
			
			CounterB counterB = new CounterB();
			
			method.add(counterB);
			
			I[] objs = {a, b, c, d};
			
			for (I obj : objs) {
				method.invoke(obj);
			}
			
			System.out.println("Counter A: " + counterA.count);
			System.out.println("Counter B: " + counterB.count);
		}
		catch (Throwable e) {
			System.err.println(e);
		}
		
	}
}
