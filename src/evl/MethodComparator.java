package evl;

public interface MethodComparator<Data> {

	int compare(MethodItem<Data> m1, MethodItem<Data> m2);
}
