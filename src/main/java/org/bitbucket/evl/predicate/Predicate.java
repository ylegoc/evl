package org.bitbucket.evl.predicate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bitbucket.evl.exception.MethodInsertionException;

public class Predicate implements Comparable<Predicate> {

	static public class InvocationException extends RuntimeException {

		private static final long serialVersionUID = 6876280226858995852L;

		public InvocationException() {
			super("Predicate invocation exception");
		}
	}
	
	private Method method;
	private Object object;
	private Object[] args;
	
	public Predicate(Object object, String name, Class<?>... parameterTypes) {
		
		try {
			this.method = object.getClass().getMethod(name, parameterTypes);
			this.method.setAccessible(true);
			
			this.object = object;	
		}
		catch (NoSuchMethodException | SecurityException e) {
			throw new MethodInsertionException();
		}
	}
	
	public void setArgs(Object[] args) {
		this.args = args;
	}
	
	@Override
	public int compareTo(Predicate other) {
		
		if (other == null) {
			return 1;
		}
		
		try {
			Boolean thisValue = (Boolean)method.invoke(object, args);
			Boolean otherValue = (Boolean)other.method.invoke(other.object, args);
		
			return thisValue.compareTo(otherValue);
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new InvocationException();
		}
	}

}
