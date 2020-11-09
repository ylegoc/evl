package eu.daproject.evl.examples.comparator;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method2;
import eu.daproject.evl.examples.common.Box;
import eu.daproject.evl.examples.common.Car;
import eu.daproject.evl.examples.common.Element;
import eu.daproject.evl.examples.common.Tallboy;

public class BoyMain {
	
	public static void test(Method2<Integer> comparator, Element element1, Element element2) throws Throwable {
		
		int result = comparator.invoke(element1, element2);
		
		if (result == 1) {
			System.out.println(element1 + " is more interesting than " + element2); 
		}
		else if (result == -1) {
			System.out.println(element1 + " is less interesting than " + element2); 
		}
		else {
			System.out.println(element1 + " is as interesting as " + element2);
		}
	}
	
	public static void main(String[] args) throws Throwable {
			
		Method2<Integer> interestComparator = new Method2<Integer>()
				.symmetricComparator()
				.add(new Cases() {
			
			int match(Car car, Element element) {
				return 1;
			}
			
			int match(Element element, Car car) {
				return -1;
			}
			
			int match(Element element1, Element element2) {
				return 0;
			}
		});
		
		try {
			test(interestComparator, new Car("blue"), new Car("red"));
		}
		catch (Throwable e) {
			System.err.println("Error in comparison : " + e);
		}
		
		interestComparator.add(new Cases() {
			
			int match(Car car1, Car car2) {
				return 0;
			}
		});
		
		try {
			test(interestComparator, new Car("blue"), new Car("red"));
		}
		catch (Throwable e) {
			System.err.println("Error in comparison : " + e);
		}
		
		Element[] elements = new Element[] {new Car("blue"), new Car("red"), new Tallboy("wood"), new Box(12)};
		
		for (Element element1 : elements) {
			for (Element element2 : elements) {
				test(interestComparator, element1, element2);				
			}
		}
	}
}
