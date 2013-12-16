package evl.comparators;

public class ProductDistanceComparator {

	public static int compare(int[] d1, int[] d2) {
		
		int less = 0;
		int greater = 0;
		
		for (int i = 0; i < d1.length; i++) {
			if (d1[i] < d2[i]) {
				less++;
			} else if (d1[i] > d2[i]) {
				greater++;
			}
		}
		
		if (less > 0 && greater == 0) {
			return -1;
		}
		
		if (greater > 0 && less == 0) {
			return 1;
		}
		
		// equality
		return 0;
	}
}
