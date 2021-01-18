package eu.daproject.evl.examples.printer;

import eu.daproject.evl.Cases;
import eu.daproject.evl.examples.common.Element;
import eu.daproject.evl.examples.common.Tallboy;

public class SmallTallboy extends Tallboy {

	public SmallTallboy() {
		super("wood");
	}
	
	static {
		Print.method().add(new Cases() {
			String match(XML xml, SmallTallboy tallboy) {
				String result = "<smallTallboy>\n";
				
				for (Element element : tallboy.getElements()) {
					try {
						result += "  " + Print.method().invoke(xml, element) + "\n";
					} catch (Throwable e) {
						result += "  \n<notPrintable/>\n";
					}
				}
				result += "</smallTallboy>";
				
				return result;
			}
		});
	}
}
