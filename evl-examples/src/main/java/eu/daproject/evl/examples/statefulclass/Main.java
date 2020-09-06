package eu.daproject.evl.examples.statefulclass;

import eu.daproject.evl.examples.common.Adult;
import eu.daproject.evl.examples.common.Kid;
import eu.daproject.evl.examples.common.Teen;
import eu.daproject.evl.examples.common.Teen.Sex;

public class Main {
	
	public static void main(String[] args) {
		{	
			PersonFinder finder = new PersonFinder();
			
			System.out.println("Person is " + finder.find(new Kid(6), 10));
			System.out.println("Person is " + finder.find(new Teen(Sex.GIRL), 10));
			
			finder.setRainyWeather();
			
			System.out.println("Person is " + finder.find(new Kid(6), 18));
			System.out.println("Person is " + finder.find(new Teen(Sex.GIRL), 18));
			System.out.println("Person is " + finder.find(new Adult(), 18));
		}
		{
			PersonFinder finder = new AdultPersonFinder();
			
			System.out.println("Person is " + finder.find(new Adult(), 21));
		}
	}
}
