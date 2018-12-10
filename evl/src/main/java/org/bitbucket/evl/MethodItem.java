package org.bitbucket.evl;



public class MethodItem extends DispatchableMethod {

	private int[] distance;
	
	public MethodItem(DispatchableMethod method, int[] distance) {
		super(method.getClassTuple(), method.getMethod(), method.getObject());
		setData(method.getData());
		
		this.distance = distance;
	}
	
	public int[] getDistanceTuple() {
		return distance;
	}

}
