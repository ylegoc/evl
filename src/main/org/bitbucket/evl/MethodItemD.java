package org.bitbucket.evl;



public class MethodItemD<DataType> extends DispatchableMethodD<DataType> {

	private int[] distance;
	
	public MethodItemD(DispatchableMethodD<DataType> method, int[] distance) {
		super(method.getClassTuple(), method.getMethod(), method.getObject());
		setData(method.getData());
		
		this.distance = distance;
	}
	
	public int[] getDistanceTuple() {
		return distance;
	}

}
