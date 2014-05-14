package tutorials.tutorial4;

import evl.base.Method1;

public class Process {
	private static final Method1<Integer> instance = new Method1<Integer>();
	
	public static Method1<Integer> method() {
		return instance;
	}
}
