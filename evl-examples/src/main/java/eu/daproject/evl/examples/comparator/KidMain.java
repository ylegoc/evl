package eu.daproject.evl.examples.comparator;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method2;
import eu.daproject.evl.examples.common.Box;
import eu.daproject.evl.examples.common.Car;
import eu.daproject.evl.examples.common.Element;
import eu.daproject.evl.examples.common.GoldenBox;
import eu.daproject.evl.examples.common.Tallboy;

public class KidMain {
	
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
		
		// Add case.
		interestComparator.add(new Cases() {
			
			int match(Car car1, Car car2) {
				return Integer.compare(car1.getColor().length(), car2.getColor().length());
			}
		});
		
		try {
			test(interestComparator, new Car("blue"), new Car("red"));
		}
		catch (Throwable e) {
			System.err.println("Error in comparison : " + e);
		}
		
		// Test Car, Tallboy, Box.
		{
			Element[] elements = new Element[] {new Car("blue"), new Car("red"), new Tallboy("wood"), new Box(12)};
			
			System.out.println();
			System.out.println("Test Car, Tallboy, Box");
			
			for (Element element1 : elements) {
				for (Element element2 : elements) {
					test(interestComparator, element1, element2);				
				}
			}
		}
		
		// Add the GoldenBox.
		{
			interestComparator.add(new Cases() {
				
				int match(GoldenBox box, Element element) {
					return 1;
				}
				
				int match(Element element, GoldenBox box) {
					return -1;
				}
				
				int match(GoldenBox box1, GoldenBox box2) {
					return Integer.compare(box1.getSize(), box2.getSize());
				}
			});

			try {
				test(interestComparator, new Car("blue"), new GoldenBox(15));
			}
			catch (Throwable e) {
				System.err.println("Error in comparison : " + e);
			}
			
			// Add cases.
			interestComparator.add(new Cases() {
				
				int match(GoldenBox box, Car car) {
					return 1;
				}
				
				int match(Car car, GoldenBox box) {
					return -1;
				}
			});

			Element[] elements = new Element[] {new Car("blue"), new Car("red"), new Tallboy("wood"), new Box(12), new GoldenBox(15)};
						
			System.out.println();
			System.out.println("Test Car, Tallboy, Box, GoldenBox");
				
			for (Element element1 : elements) {
				for (Element element2 : elements) {
					test(interestComparator, element1, element2);				
				}
			}
		}
		
	}
}
