package eu.daproject.evl.examples.queue;

import eu.daproject.evl.examples.common.Box;
import eu.daproject.evl.examples.common.Room;
import eu.daproject.evl.examples.common.Tallboy;

public class ContainerCounter {

	private int boxCount;
	private int roomCount;
	private int tallboyCount;
	
	void reset() {
		boxCount = 0;
		roomCount = 0;
		tallboyCount = 0;
	}
	
	public void count(Box box) {
		boxCount++;
	}
	
	public void count(Room room) {
		roomCount++;
	}
	
	public void count(Tallboy tallboy) {
		tallboyCount++;
	}

	public int getBoxCount() {
		return boxCount;
	}

	public int getRoomCount() {
		return roomCount;
	}

	public int getTallboyCount() {
		return tallboyCount;
	}
	
}
