package org.bitbucket.evl.tutorial4;

import org.bitbucket.evl.Method1;

public class Process {
	private static final Method1<Integer> instance = new Method1<Integer>();
	
	public static Method1<Integer> instance() {
		return instance;
	}
}
