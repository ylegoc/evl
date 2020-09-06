package eu.daproject.evl.examples.common;

import java.util.ArrayList;
import java.util.List;

public class Container implements Element {
	
	private List<Element> elements = new ArrayList<Element>();
	public void add(Element element) { elements.add(element); }
	public List<Element> getElements() { return elements; }
}
