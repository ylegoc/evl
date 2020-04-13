package eu.daproject.evl.exception;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import eu.daproject.evl.util.ClassTuple;

/**
 * Exception thrower when there is no matching method available in the multimethod.
 * It is used to cache the exception.
 *
 */
public class NoMatchingMethodExceptionThrower implements ExceptionThrower {

	private MethodHandles.Lookup lookup;
	private ClassTuple classTuple;
	private String message;
	
	/**
	 * Constructs the exception thrower.
	 * @param lookup the method handle lookup
	 * @param classTuple the class tuple
	 * @param message the message
	 */
	public NoMatchingMethodExceptionThrower(MethodHandles.Lookup lookup, ClassTuple classTuple, String message) {
		this.lookup = lookup;
		this.classTuple = classTuple;
		this.message = message;
	}
	
	@Override
	public void invoke() throws InvocationException {
		throw new NoMatchingMethodException(classTuple, message);
	}
	
	/**
	 * Methods inserted in the cache that throws the {@link NoMatchingMethodException} exception.
	 * @param objects any objects
	 * @throws NoMatchingMethodException if there is no matching method
	 */
	public void invoke(Object...objects) throws NoMatchingMethodException {
		throw new NoMatchingMethodException(classTuple, message);
	}
	
	/**
	 * Gets the method handle to the invoke method.
	 * @return the method handle
	 */
	public MethodHandle getMethodHandle() {
		try {
			return lookup.findVirtual(this.getClass(), "invoke", MethodType.methodType(void.class, Object[].class))
					.bindTo(this)
					// It is necessary to set with varargs so that the method adapts to any number of arguments.
					.withVarargs(true);
		}
		catch (NoSuchMethodException | IllegalAccessException e) {
			throw new UnexpectedException("Cannot get the method handle of the invoke method of an exception thrower");
		}
	}
}
