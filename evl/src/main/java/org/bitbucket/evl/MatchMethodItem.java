package org.bitbucket.evl;



public class MatchMethodItem extends MatchMethod {

	private int[] distance;
	
	MatchMethodItem(MatchMethod method, int[] distance) {
		super(method.getClassTuple(), method.getMethod(), method.getObject());
		setData(method.getData());
		
		this.distance = distance;
	}
	
	public int[] getDistanceTuple() {
		return distance;
	}

}
