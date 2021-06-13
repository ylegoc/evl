package eu.daproject.evl.examples.treewalker;

import eu.daproject.evl.examples.common.Container;
import eu.daproject.evl.examples.common.Element;

public class TreeTraversal {

	public void traverse(Container container, TreeWalker walker) throws Throwable {
		
		for (Element element : container.getElements()) {
			walker.walk(element);
		}
	}
	
	public void traverse(Element element, TreeWalker walker) {
	}
}
