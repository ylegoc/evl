package eu.daproject.evl.examples.treewalker;

import eu.daproject.evl.examples.common.Element;

public class TreePrinter {

	private Space space = new Space();
	
	public void enter(Element element) {
		
		System.out.println(space.get() + element);
		
		space.increment();
	}
	
	public void leave(Element element) {
		space.decrement();
	}
	
}
