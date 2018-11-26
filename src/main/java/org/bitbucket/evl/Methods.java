package org.bitbucket.evl;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Methods {

	public void check(MethodHandles.Lookup lu) {

		
		//MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandles.Lookup lookup = lu;
		
		System.out.println("lookup = " + lookup);
		
		Method[] methods = getClass().getDeclaredMethods();
		for (Method m : methods) {
			
			MethodHandle methodHandle = null;
			try {
				System.out.println("findVirtual " + m.getDeclaringClass() + " " + m.getName() + " " + m.getReturnType() + " " + m.getParameterTypes());
				
				
				methodHandle = lookup.findVirtual(m.getDeclaringClass(), m.getName(), MethodType.methodType(m.getReturnType(), m.getParameterTypes()));
				
				System.out.println("findVirtual ok");
			}
			catch (Throwable e) {
				e.printStackTrace();
			}
			
		}
	}

}
