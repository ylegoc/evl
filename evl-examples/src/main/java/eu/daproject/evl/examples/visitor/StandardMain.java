package eu.daproject.evl.examples.visitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eu.daproject.evl.examples.common.Car;
import eu.daproject.evl.examples.common.Doll;
import eu.daproject.evl.examples.common.Toy;

public class StandardMain {

	public static void main(String[] args) {

		ArrayList<Toy> toys = new ArrayList<Toy>();
		toys.add(new Car("red"));
		toys.add(new Doll(10));
		
		for (Toy toy : toys) {
			
			if (toy instanceof Car) {
				Car car = (Car)toy;
				System.out.println("Car with color " + car.getColor());
			}
			
			if (toy instanceof Doll) {
				Doll doll = (Doll)toy;
				System.out.println("Doll with height " + doll.getHeight());
			}
			
		}
	}
}
