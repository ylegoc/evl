package tutorials.tutorial3;

import evl.base.Method1;

public class Process {
	private static final Method1<Integer> instance = Method1.<Integer>builder().build();
	
	public static Method1<Integer> method() {
		return instance;
	}
}
