package eu.daproject.evl.examples.tostring;

import eu.daproject.evl.Cases;
import eu.daproject.evl.examples.common.Doll;

public class SmallDoll extends Doll {

	private String hair;
	
	public SmallDoll(String hair) {
		super(13);
		this.hair = hair;
	}
	
	static {
		ToString.method().add(new Cases() {
			String match(XML xml, SmallDoll doll) {
				return "<smallDoll height=\"" + doll.getHeight() + "\" hair=\"" + doll.hair + "\"/>";
			}
		});
	}
}
