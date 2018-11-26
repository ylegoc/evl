package org.bitbucket.evl.tutorial1;

import java.lang.invoke.MethodHandles;

import org.bitbucket.evl.Method1;
import org.bitbucket.evl.Methods;
import org.bitbucket.evl.classes.A;
import org.bitbucket.evl.classes.B;
import org.bitbucket.evl.classes.C;

/**
 * Simple example.
 * @author yan
 *
 */
public class Main {
	
	static class MyMethods extends Methods {
		public int match(B obj) {
			return 1 + obj.getB();
		}

		public int match(C obj) {
			return 2 + obj.getC();
		}
	}
	
	public int match(B obj) {
		return 1 + obj.getB();
	}

	public int match(C obj) {
		return 2 + obj.getC();
	}
	
	public static void main(String[] args) throws Throwable {
		
		A b = new B(1, 2);
		A c = new C(2, -5);
		
		Foo foo = new Foo();
		
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		System.out.println("lookup = " + lookup + " " + lookup.lookupModes());
		
		Method1<Integer> process = new Method1<Integer>()
				//				.add(foo, "process");
						.add(new Main());
		
		Method1<Integer> process2 = new Method1<Integer>()
				.add(lookup, new Methods() {
					public int match(B obj) {
						return 1 + obj.getB();
					}

					public int match(C obj) {
						return 2 + obj.getC();
					}
		});
		
		Method1<Integer> process4 = new Method1<Integer>()
				.add(new Methods() {
					public int match(B obj) {
						return 1 + obj.getB();
					}

					public int match(C obj) {
						return 2 + obj.getC();
					}
		});
		
		System.out.println(process4.invoke(b));
		System.out.println(process4.invoke(c));
	}
}
