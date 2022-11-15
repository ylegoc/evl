package eu.daproject.evl.examples.factory;

import eu.daproject.evl.Cases;
import eu.daproject.evl.examples.common.Adult;
import eu.daproject.evl.examples.common.Game;
import eu.daproject.evl.examples.common.Inside;
import eu.daproject.evl.examples.common.Kid;
import eu.daproject.evl.examples.common.Location;
import eu.daproject.evl.examples.common.Outside;
import eu.daproject.evl.examples.common.Person;
import eu.daproject.evl.examples.common.Teen;
import eu.daproject.evl.examples.common.Teen.Sex;
import eu.daproject.evl.examples.common.Toy;

public class Main {
	
	public static void testToy(Factory factory, Person person, Location location) throws Throwable {
		System.out.println("Toy created for " + person + " is " + factory.createToy(person, location));
	}
	
	public static void testContainer(Factory factory, Person person, Location location) throws Throwable {
		System.out.println("Container created for " + person + " is " + factory.createContainer(person, location));
	}
	
	public static void main(String[] args) throws Throwable {
		
		Factory factory = new Factory();
		
		Person kid = new Kid(5);
		Person teen = new Teen(Sex.GIRL);
		Person adult = new Adult();
		
		testToy(factory, kid, new Inside());
		testToy(factory, teen, new Outside());
		
		testContainer(factory, teen, new Outside());
		testContainer(factory, adult, new Inside());
		
		factory.addToyCases(new Cases() {
			
			Toy match(Adult adult, Outside outside) {
				return new Game(4);
			}
		});
		
		testToy(factory, adult, new Outside());
	}
}
