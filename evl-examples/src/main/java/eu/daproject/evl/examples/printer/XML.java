package eu.daproject.evl.examples.printer;

public class XML {

	static XML instance = new XML();
	
	static XML getInstance() {
		return instance;
	}
	
	private XML() {
	}
}
