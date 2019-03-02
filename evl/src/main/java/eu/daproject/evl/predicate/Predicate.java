package eu.daproject.evl.predicate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import eu.daproject.evl.exception.MethodInsertionException;

public class Predicate implements Comparable<Predicate> {

	static public class InvocationException extends RuntimeException {

		private static final long serialVersionUID = 6876280226858995852L;

		public InvocationException() {
			super("Predicate invocation exception");
		}
	}
	
	private Method method;
	private Object object;
	private ThreadLocal<Object[]> threadLocalArgs = new ThreadLocal<Object[]>();
	
	public Predicate(Object object, String name, Class<?>... parameterTypes) {
		
		try {
			this.method = object.getClass().getMethod(name, parameterTypes);
			this.method.setAccessible(true);
			
			this.object = object;	
		}
		catch (NoSuchMethodException | SecurityException e) {
			throw new MethodInsertionException(e.getMessage());
		}
	}
	
	public void setArgs(Object[] args) {
		threadLocalArgs.set(args);
	}
	
	@Override
	public int compareTo(Predicate other) {
		
		if (other == null) {
			return 1;
		}
		
		try {
			Boolean thisValue = (Boolean)method.invoke(object, threadLocalArgs.get());
			Boolean otherValue = (Boolean)other.method.invoke(other.object, threadLocalArgs.get());
		
			return thisValue.compareTo(otherValue);
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new InvocationException();
		}
	}

}
