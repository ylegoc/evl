package org.bitbucket.evl;

public class Priority implements Comparable<Priority> {

	private int value;
	
	protected Priority(int value) {
		this.value = value;
	}
	
	public static Priority valueOf(int value) {
		return new Priority(value);
	}
	
	@Override
	public int compareTo(Priority other) {
		
		if (other == null) {
			return 1;
		}
		
		return Integer.valueOf(value).compareTo(Integer.valueOf(other.value));
	}

}
