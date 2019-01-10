package org.bitbucket.evl;

public class Switch extends Method1<Void> {

	public static Switch with(Cases cases) {
		return (Switch)new Switch().add(cases);
	}
}
