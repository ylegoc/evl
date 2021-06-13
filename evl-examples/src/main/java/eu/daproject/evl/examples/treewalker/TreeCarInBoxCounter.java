package eu.daproject.evl.examples.treewalker;

import java.util.ArrayDeque;
import java.util.Deque;

import eu.daproject.evl.examples.common.Box;
import eu.daproject.evl.examples.common.Car;
import eu.daproject.evl.examples.common.Element;

public class TreeCarInBoxCounter {

	private Deque<Integer> counters = new ArrayDeque<Integer>();
	
	public void enter(Element element) {
	}
	
	public void enter(Box box) {
		counters.push(0);
	}
	
	public void enter(Car car) {
		Integer count = counters.pop();
		count++;
		counters.push(count);
	}
	
	public void leave(Element element) {
	}
	
	public void leave(Box box) {
		int count = counters.pop();
		
		System.out.println(box + " contains " + count + " cars");
	}
}
