package eu.daproject.evl.examples.factory;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method2;
import eu.daproject.evl.examples.common.Adult;
import eu.daproject.evl.examples.common.Box;
import eu.daproject.evl.examples.common.Car;
import eu.daproject.evl.examples.common.Container;
import eu.daproject.evl.examples.common.Doll;
import eu.daproject.evl.examples.common.Game;
import eu.daproject.evl.examples.common.GoldenBox;
import eu.daproject.evl.examples.common.Inside;
import eu.daproject.evl.examples.common.Kid;
import eu.daproject.evl.examples.common.Location;
import eu.daproject.evl.examples.common.Outside;
import eu.daproject.evl.examples.common.Person;
import eu.daproject.evl.examples.common.Room;
import eu.daproject.evl.examples.common.Tallboy;
import eu.daproject.evl.examples.common.Teen;
import eu.daproject.evl.examples.common.Toy;

public class Factory {

	public static class CannotCreateException extends Throwable {}
	
	protected Method2<Toy> toyFactory = new Method2<Toy>();
	protected Method2<Container> containerFactory = new Method2<Container>();
	
	public Factory() {
		
		toyFactory.add(new Cases() {
			
			Toy match(Kid kid, Inside inside) {
				return new Doll(20);
			}
			
			Toy match(Kid kid, Outside outside) {
				return new Car("red");
			}
			
			Toy match(Teen teen, Inside inside) {
				return new Game(3);
			}
			
			Toy match(Teen teen, Outside outside) {
				return new Game(6);
			}
			
			Toy match(Adult adult, Inside inside) {
				return new Game(2);
			}
		});
		
		containerFactory.add(new Cases() {
			
			Container match(Kid kid, Inside inside) {
				return new Tallboy("wood");
			}
			
			Container match(Kid kid, Outside outside) {
				return new Box(20);
			}
			
			Container match(Teen teen, Inside inside) {
				return new Tallboy("plastic");
			}
			
			Container match(Teen teen, Outside outside) {
				return new GoldenBox(30);
			}
			
			Container match(Adult adult, Inside inside) {
				return new Room("white");
			}
		});
	}
	
	public void addToyCases(Cases cases) {
		toyFactory.add(cases);
	}
	
	public void addContainerCases(Cases cases) {
		containerFactory.add(cases);
	}
	
	public Toy createToy(Person person, Location location) throws CannotCreateException {
		try {
			return toyFactory.invoke(person, location);
		}
		catch (Throwable e) {
			throw new CannotCreateException();
		}
	}
	
	public Container createContainer(Person person, Location location) throws CannotCreateException {
		try {
			return containerFactory.invoke(person, location);
		}
		catch (Throwable e) {
			throw new CannotCreateException();
		}
	}
}
