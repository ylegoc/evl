package evl.comparators;

public class LexicographicDistanceComparator {

	public static int compare(int[] d1, int[] d2) {
		
		for (int i = 0; i < d1.length; i++) {
			if (d1[i] < d2[i]) {
				return -1;
			} else if (d1[i] > d2[i]) {
				return 1;
			}
		}
		
		return 0;
	}
}
