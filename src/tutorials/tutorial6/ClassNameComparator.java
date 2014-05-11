package tutorials.tutorial6;

import evl.data.MethodComparatorD;
import evl.data.MethodItemD;

public class ClassNameComparator extends MethodComparatorD<Void> {

	@Override
	public int compare(MethodItemD<Void> m1, MethodItemD<Void> m2) {
		
		int length = m1.getClassTuple().get().length;
		String name1 = "";
		String name2 = "";
		
		for (int i = 0; i < length; ++i) {
			name1 += m1.getClassTuple().get()[i].getName() + ";";
			name2 += m2.getClassTuple().get()[i].getName() + ";";
		}
		
		int result = name1.compareTo(name2);
		System.out.println("compare " + name1 + " to " + name2);
		
		return result;
	}

}
