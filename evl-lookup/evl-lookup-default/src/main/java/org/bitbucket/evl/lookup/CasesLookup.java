package org.bitbucket.evl.lookup;

import java.lang.invoke.MethodHandles;

public class CasesLookup {

	public static MethodHandles.Lookup privateLookupIn(Class<?> classInstance, MethodHandles.Lookup lookup) throws IllegalAccessException, SecurityException {
		return MethodHandles.privateLookupIn(classInstance.getEnclosingClass(), lookup);
	}
}
