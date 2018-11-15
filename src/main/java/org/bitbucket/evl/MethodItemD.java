package org.bitbucket.evl;



public class MethodItemD extends DispatchableMethodD {

	private int[] distance;
	
	public MethodItemD(DispatchableMethodD method, int[] distance) {
		super(method.getClassTuple(), method.getMethod(), method.getObject());
		setData(method.getData());
		
		this.distance = distance;
	}
	
	public int[] getDistanceTuple() {
		return distance;
	}

}
