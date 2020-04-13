package eu.daproject.evl.exception;

/**
 * Base interface for an exception thrower. 
 *
 */
public interface ExceptionThrower {

	/**
	 * Invokes the thrower.
	 * @throws InvocationException the invocation exception
	 */
	void invoke() throws InvocationException;
}
