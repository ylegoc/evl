package eu.daproject.evl.exception;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import eu.daproject.evl.util.ClassTuple;

/**
 * Exception thrower when there when there are at least two matching methods in the list of the minimum methods.
 * It is used to cache the exception.
 *
 */
public class AmbiguousMethodExceptionThrower {

	private MethodHandles.Lookup lookup;
	private ClassTuple classTuple;
	private String possibleMethods;
	
	/**
	 * Constructs the exception thrower.
	 * @param lookup the method handle lookup
	 * @param classTuple the class tuple
	 * @param possibleMethods the possible methods string
	 */
	public AmbiguousMethodExceptionThrower(MethodHandles.Lookup lookup, ClassTuple classTuple, String possibleMethods) {
		this.lookup = lookup;
		this.classTuple = classTuple;
		this.possibleMethods = possibleMethods;
	}
	
	/**
	 * Methods inserted in the cache that throws the {@link AmbiguousMethodException} exception.
	 * @param objects any objects
	 * @throws AmbiguousMethodException if there are more than one minimum matching method
	 */
	public void invoke(Object...objects) throws AmbiguousMethodException {
		throw new AmbiguousMethodException(classTuple, possibleMethods);
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
