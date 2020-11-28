package eu.daproject.evl.examples.observer;

import eu.daproject.evl.Cases;
import eu.daproject.evl.examples.common.Box;
import eu.daproject.evl.examples.common.Car;
import eu.daproject.evl.examples.common.Doll;
import eu.daproject.evl.examples.common.Game;
import eu.daproject.evl.examples.common.GoldenBox;
import eu.daproject.evl.examples.common.Room;
import eu.daproject.evl.examples.common.Tallboy;
import eu.daproject.evl.examples.common.Toy;

public class Main {
	
	public static void main(String[] args) throws Throwable {

		ToySource source = new ToySource();
		
		source.addContainerCases(new Cases() {
			
			boolean match(Tallboy tallboy, Toy toy) {
				System.out.println("The tallboys accept every toy, even the adult ones!");
				return true;
			}
			
			boolean match(Room room, Toy toy) {
				System.out.println("A room is not supposed to be a mess!");
				return false;
			}
			
			boolean match(Room room, Game game) {
				System.out.println("Games are the only exception, especially the ones that do not enter inside a tallboy!");
				return true;
			}
			
			boolean match(Box box, Toy toy) {
				System.out.println("A box is usually too small to contain toys!");
				return false;
			}
			
			boolean match(Box box, Car car) {
				System.out.println("1:43 scale cars enter inside a box!");
				return true;
			}
			
			boolean match(GoldenBox box, Toy toy) {
				System.out.println("What contains the golden box is unfortunately a secret!");
				return false;
			}
			
		});
		
		source.addContainer(new Tallboy("wood"));
		source.addContainer(new Room("blue"));
		source.addContainer(new Box(12));
		source.addContainer(new GoldenBox(10));
		
		source.update(new Car("red"));
		source.update(new Doll(30));
		source.update(new Game(4));
	}
}
