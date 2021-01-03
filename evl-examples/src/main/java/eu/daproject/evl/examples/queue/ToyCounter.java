package eu.daproject.evl.examples.queue;

import eu.daproject.evl.examples.common.Car;
import eu.daproject.evl.examples.common.Doll;
import eu.daproject.evl.examples.common.Game;

public class ToyCounter {

	private int carCount;
	private int dollCount;
	private int gameCount;

	void reset() {
		carCount = 0;
		dollCount = 0;
		gameCount = 0;
	}
	
	public void count(Car car) {
		carCount++;
	}
	
	public void count(Doll doll) {
		dollCount++;
	}
	
	public void count(Game game) {
		gameCount++;
	}

	public int getCarCount() {
		return carCount;
	}

	public int getDollCount() {
		return dollCount;
	}

	public int getGameCount() {
		return gameCount;
	}
	
}
