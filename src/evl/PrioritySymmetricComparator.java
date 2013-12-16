package evl;


public class PrioritySymmetricComparator<DataType extends Comparable<DataType>> extends SymmetricComparator<DataType> {

	@Override
	public int compare(MethodItem<DataType> m1, MethodItem<DataType> m2) {
		
		int comparison = super.compare(m1, m2);
		if (comparison == 0) {
			return m1.getData().compareTo(m2.getData());
		}
		
		return comparison;
	}

}
