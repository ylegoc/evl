package eu.daproject.evl.examples.strategy;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method3;
import eu.daproject.evl.examples.common.Adult;
import eu.daproject.evl.examples.common.Car;
import eu.daproject.evl.examples.common.Game;
import eu.daproject.evl.examples.common.Kid;
import eu.daproject.evl.examples.common.Person;
import eu.daproject.evl.examples.common.Rainy;
import eu.daproject.evl.examples.common.Sunny;
import eu.daproject.evl.examples.common.Teen;
import eu.daproject.evl.examples.common.Teen.Sex;
import eu.daproject.evl.examples.common.Toy;
import eu.daproject.evl.examples.common.Weather;

public class Main {
		
	public static class CannotPlayException extends Throwable {}
	
	public static void test(Method3<Integer> playDuration, Person person, Toy toy, Weather weather) throws Throwable {
		try {
			System.out.println(person + " is playing " + toy + " with " + weather + " weather during " + playDuration.invoke(person, toy, weather) + " minutes");
		}
		catch (CannotPlayException e) {
			System.out.println(person + " cannot play " + toy + " with " + weather + " weather");
		}
	}
	
	public static void main(String[] args) throws Throwable {
	
		Method3<Integer> playDuration = new Method3<Integer>()
				.add(new Cases() {
			
			int match(Person person, Toy toy, Weather weather) throws CannotPlayException {
				throw new CannotPlayException();
			}
					
			int match(Kid kid, Car car, Sunny sunny) {
				if (kid.getAge() > 6) {
					return 40;
				}
				return 20;
			}
			
			int match(Kid kid, Car car, Rainy rainy) {
				if (kid.getAge() > 6) {
					return 50;
				}
				return 25;
			}
			
			int match(Teen teen, Game game, Weather weather) {
				return 100;
			}
			
			int match(Adult adult, Game game, Rainy rainy) {
				return 60;
			}
		});
		
		Person kid = new Kid(5);
		Person teen = new Teen(Sex.GIRL);
		Person adult = new Adult();
		
		Toy car = new Car("blue");
		Toy game = new Game(3);
		
		Weather weather = new Rainy();
		
		test(playDuration, kid, car, weather);
		test(playDuration, teen, game, weather);
		test(playDuration, adult, game, weather);
		test(playDuration, adult, car, weather);
		
		weather = new Sunny();
		
		test(playDuration, kid, car, weather);
		test(playDuration, teen, game, weather);
		test(playDuration, adult, game, weather);
		test(playDuration, adult, car, weather);
		
		playDuration.add(new Cases() {
			
			int match(Teen teen, Game game, Sunny sunny) {
				return 90;
			}
			
			int match(Teen teen, Game game, Rainy rainy) {
				return 110;
			}
		});
		
		test(playDuration, teen, game, weather);
	}
}
