package firstmodule.test;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method1;
import eu.daproject.evl.exception.InvocationException;

public class TestModule {

	private Method1<Void> m1 = new Method1<Void>();
	
	public TestModule() {
		
		m1.add(this, "foo");
		m1.add(TestModule.class, "foos");
		
		m1.add(new Cases() {
			void match(Double d) {
				System.out.println("I am the double " + d);
			}
			
			void match(String s) {
				System.out.println("I am the string " + s);
			}
		});
	}
	
	public Method1<Void> getMethod() {
		return m1;
	}
	
	public void foo(Integer i) {
		System.out.println("Foo " + i);
	}
	
	public static void foos(Float f) {
		System.out.println("Foos " + f);
	}
	
	public static void main(String[] args) throws Throwable {
		
		System.out.println("Testing EVL module");
		
		Method1<Void> m = new Method1<Void>();
		
		TestModule tm = new TestModule();
		
		m.add(tm, "foo");
		m.add(TestModule.class, "foos");
		
		m.add(new Cases() {
			void match(Double d) {
				System.out.println("I am the double " + d);
			}
			
			void match(String s) {
				System.out.println("I am the string " + s);
			}
		});
		
		try {
			m.invoke(Integer.valueOf(11));
		}
		catch (InvocationException e) {
			
		}
	}
}
