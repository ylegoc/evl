package eu.daproject.evl.examples.statefulclass;

import eu.daproject.evl.examples.common.Adult;
import eu.daproject.evl.examples.common.Weather;

public class AdultPersonFinder extends PersonFinder {

	protected Location findMatch(Weather weather, Adult adult, int hour) {
		if (hour > 8 && hour < 20) {
			return Location.OUTSIDE;
		}
		return Location.INSIDE;
	}
}
