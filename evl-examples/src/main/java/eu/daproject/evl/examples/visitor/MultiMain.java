package eu.daproject.evl.examples.visitor;

import java.util.ArrayList;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method1;
import eu.daproject.evl.Method2;
import eu.daproject.evl.examples.common.Car;
import eu.daproject.evl.examples.common.Doll;
import eu.daproject.evl.examples.common.Game;
import eu.daproject.evl.examples.common.Kid;
import eu.daproject.evl.examples.common.Person;
import eu.daproject.evl.examples.common.Teen;
import eu.daproject.evl.examples.common.Toy;

public class MultiMain {

	public static class ToyCannotBePlayedException extends Throwable {}
	
	public static void main(String[] args) {

		ArrayList<Toy> toys = new ArrayList<Toy>();
		toys.add(new Car("red"));
		toys.add(new Doll(10));
		
		{
			Method1<Void> play = new Method1<Void>().add(new Cases() {
				
				void match(Car car) {
					System.out.println("Car with color " + car.getColor());
				}
				
				void match(Doll doll) {
					System.out.println("Doll with height " + doll.getHeight());
				}
				
			});
			
			for (Toy toy : toys) {
				try {
					play.invoke(toy);
				}
				catch (Throwable e) {
					System.out.println("Toy cannot be played");
				}
			}
			
			play.add(new Cases() {
				
				void match(Game game) {
					System.out.println("Game with players " + game.getPlayers());
				}
				
			});
			
			toys.add(new Game(6));
			
			for (Toy toy : toys) {
				try {
					play.invoke(toy);
				}
				catch (Throwable e) {
					System.out.println("Toy cannot be played");
				}
			}
		}
		
		{
			Method2<Void> play = new Method2<Void>().add(new Cases() {
				
				void match(Kid kid, Car car) {
					System.out.println(kid.getAge() + " years old kid plays with car with color " + car.getColor());
				}
				
				void match(Kid kid, Doll doll) {
					System.out.println(kid.getAge() + " years old kid plays with doll with height " + doll.getHeight());
				}
				
				void match(Teen teen, Game game) {
					System.out.println(teen.getSex() + " teen plays with game with players " + game.getPlayers());
				}
				
			});
			
			Person[] persons = {new Kid(5), new Teen(Teen.Sex.BOY)};

			for (Person person : persons) {
				for (Toy toy : toys) {
					try {
						play.invoke(person, toy);
					}
					catch (Throwable e) {
						System.out.println("Toy cannot be played");
					}
				}
			}
			
			play.add(new Cases() {
				
				void match(Person person, Toy toy) throws ToyCannotBePlayedException {
					throw new ToyCannotBePlayedException();
				}
			});
			
			for (Person person : persons) {
				for (Toy toy : toys) {
					try {
						play.invoke(person, toy);
					}
					catch (ToyCannotBePlayedException e) {
						System.out.println("Toy cannot be played by any person");
					}
					catch (Throwable e) {}
				}
			}
		}
	}
	
}
