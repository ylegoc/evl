package eu.daproject.evl.examples.observer;

import java.util.ArrayList;
import java.util.List;

import eu.daproject.evl.Cases;
import eu.daproject.evl.Method2;
import eu.daproject.evl.examples.common.Container;
import eu.daproject.evl.examples.common.Toy;

public class ToySource {

	private List<Container> containers = new ArrayList<>();
	private Method2<Boolean> update = new Method2<Boolean>();
	
	void addContainer(Container container) {
		containers.add(container);
	}
	
	void addContainerCases(Cases cases) {
		update.add(cases);
	}
	  
	void update(Toy toy) {
		
		containers.forEach(container -> {
			try {
				if (update.invoke(container, toy)) {
					System.out.println(container + " added " + toy);
				}
			}
			catch (Throwable e) {
				System.err.println("Cannot update " + container +  " with " + toy);
			}	
		});
	}
}
