package eu.daproject.evl.examples.comparator;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method3;
import eu.daproject.evl.examples.common.Adult;
import eu.daproject.evl.examples.common.Box;
import eu.daproject.evl.examples.common.Car;
import eu.daproject.evl.examples.common.Container;
import eu.daproject.evl.examples.common.Element;
import eu.daproject.evl.examples.common.GoldenBox;
import eu.daproject.evl.examples.common.Kid;
import eu.daproject.evl.examples.common.Person;
import eu.daproject.evl.examples.common.Tallboy;
import eu.daproject.evl.examples.common.Toy;

public class PersonMain {

	public static void test(Method3<Integer> comparator, Person person, Element element1, Element element2) throws Throwable {
		
		int result = comparator.invoke(person, element1, element2);
		
		if (result == 1) {
			System.out.println("For " + person + ", " + element1 + " is more interesting than " + element2); 
		}
		else if (result == -1) {
			System.out.println("For " + person + ", " + element1 + " is less interesting than " + element2); 
		}
		else {
			System.out.println("For " + person + ", " + element1 + " is as interesting as " + element2);
		}
	}
	
	public static void main(String[] args) throws Throwable {
		
		Method3<Integer> interestComparator = new Method3<Integer>()
				.comparator(new PersonAndElementsComparator())
				.add(new Cases() {
			
			int match(Kid kid, Car car, Element element) {
				return 1;
			}
			
			int match(Kid kid, Element element, Car car) {
				return -1;
			}
			
			int match(Kid kid, Element element1, Element element2) {
				return 0;
			}
			
			int match(Kid kid, Car car1, Car car2) {
				return Integer.compare(car1.getColor().length(), car2.getColor().length());
			}
			
			int match(Kid kid, GoldenBox box, Element element) {
				return 1;
			}
			
			int match(Kid kid, Element element, GoldenBox box) {
				return -1;
			}
			
			int match(Kid kid, GoldenBox box1, GoldenBox box2) {
				return Integer.compare(box1.getSize(), box2.getSize());
			}
				
			int match(Kid kid, GoldenBox box, Car car) {
				return 1;
			}
			
			int match(Kid kid, Car car, GoldenBox box) {
				return -1;
			}
			
			int match(Adult adult, Container container, Toy toy) {
				return 1;
			}
			
			int match(Adult adult, Toy toy, Container container) {
				return -1;
			}
			
			int match(Adult adult, Toy toy1, Toy toy2) {
				return 0;
			}
			
			int match(Adult adult, Container container1, Container container2) {
				return 0;
			}
		});
			
		Kid kid = new Kid(5);
		Adult adult = new Adult();
		
		Element[] elements = new Element[] {new Car("blue"), new Car("red"), new Tallboy("wood"), new Box(12), new GoldenBox(15)};

		System.out.println();
		System.out.println("Test Car, Tallboy, Box, GoldenBox for Kid");
		
		for (Element element1 : elements) {
			for (Element element2 : elements) {
				test(interestComparator, kid, element1, element2);				
			}
		}
		
		System.out.println();
		System.out.println("Test Car, Tallboy, Box, GoldenBox for Adult");
		
		for (Element element1 : elements) {
			for (Element element2 : elements) {
				test(interestComparator, adult, element1, element2);				
			}
		}
	}
}
