package evl;


public class MethodItem<DataType> extends DispatchableMethod<DataType> {

	private int[] distance;
	
	public MethodItem(DispatchableMethod<DataType> method, int[] distance) {
		super(method.getClassTuple(), method.getMethod(), method.getObject());
		setData(method.getData());
		
		this.distance = distance;
	}
	
	public int[] getDistanceTuple() {
		return distance;
	}

}
