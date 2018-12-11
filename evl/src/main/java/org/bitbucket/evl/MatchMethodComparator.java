package org.bitbucket.evl;

/**
 * Class providing a base for the method comparators. The arguments passed to the calling invoke are set at thread-local.
 * They can be used for predicate based dispatch where there is no cache.
 */
public abstract class MatchMethodComparator {

	private ThreadLocal<Object[]> threadLocalArgs = new ThreadLocal<Object[]>();
	
	public Object[] args() {
		return threadLocalArgs.get();
	}

	void setArgs(Object[] args) {
		threadLocalArgs.set(args);
	}
	
	public abstract int compare(MatchMethodItem m1, MatchMethodItem m2);
	
}
