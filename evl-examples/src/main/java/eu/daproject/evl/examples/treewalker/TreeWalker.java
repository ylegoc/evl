package eu.daproject.evl.examples.treewalker;

import eu.daproject.evl.Method1;

public class TreeWalker {
	
	private Method1<Void> traverse = new Method1<Void>()
			.returnType(Void.class)
			.nonVirtualParameterTypes(TreeWalker.class);
	
	private Method1<Void> enter = new Method1<Void>()
			.returnType(Void.class)
			.nonVirtualParameterTypes();
	
	private Method1<Void> leave = new Method1<Void>()
			.returnType(Void.class)
			.nonVirtualParameterTypes();
	
	public Method1<Void> traverse() {
		return traverse;
	}
	
	public Method1<Void> enter() {
		return enter;
	}
	
	public Method1<Void> leave() {
		return leave;
	}
	
	public void walk(Object object) throws Throwable {
		enter.invoke(object);
		traverse.invoke(object, this);
		leave.invoke(object);
	}
}
