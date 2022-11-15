package eu.daproject.evl.examples.composition;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method1;
import eu.daproject.evl.Method2;
import eu.daproject.evl.examples.common.Car;
import eu.daproject.evl.examples.common.Doll;
import eu.daproject.evl.examples.common.Game;
import eu.daproject.evl.examples.common.Inside;
import eu.daproject.evl.examples.common.Kid;
import eu.daproject.evl.examples.common.Location;
import eu.daproject.evl.examples.common.Outside;
import eu.daproject.evl.examples.common.Person;
import eu.daproject.evl.examples.common.Teen;
import eu.daproject.evl.examples.common.Teen.Sex;
import eu.daproject.evl.examples.common.Toy;
import eu.daproject.evl.examples.statefulclass.PersonFinder;

public class Main {
	
	public static void main(String[] args) throws Throwable {
	
		Method2<Toy> toyFinder = new Method2<Toy>().add(new Cases() {
			
			Toy match(Kid kid, Inside inside) {
				return new Doll(15);
			}
			
			Toy match(Kid kid, Outside outside) {
				return new Car("blue");
			}
			
			Toy match(Teen teen, Location location) {
				return new Game(4);
			}
		});
		
		Method1<Toy> toyPrinter = new Method1<Toy>().add(new Cases() {
			
			String match(Car car) {
				return "Car with color " + car.getColor();
			}
			
			String match(Doll doll) {
				return "Doll with height " + doll.getHeight();
			}
			
			String match(Game game) {
				return "Game with players " + game.getPlayers();
			}
		});

		PersonFinder personFinder = new PersonFinder();
		
		Person[] persons = new Person[] {new Kid(5), new Teen(Sex.GIRL)};
		
		for (Person person : persons) {
			System.out.println(person + " plays with " + 
				toyPrinter.invoke(
					toyFinder.invoke(
							person, 
							personFinder.find(person, 10))));
		}
	}
}
