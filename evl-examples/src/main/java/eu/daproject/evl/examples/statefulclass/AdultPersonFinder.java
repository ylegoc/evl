package eu.daproject.evl.examples.statefulclass;

import eu.daproject.evl.examples.common.Adult;
import eu.daproject.evl.examples.common.Inside;
import eu.daproject.evl.examples.common.Location;
import eu.daproject.evl.examples.common.Outside;
import eu.daproject.evl.examples.common.Weather;

public class AdultPersonFinder extends PersonFinder {

	protected Location findMatch(Weather weather, Adult adult, int hour) {
		if (hour > 8 && hour < 20) {
			return new Outside();
		}
		return new Inside();
	}
}
