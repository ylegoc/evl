package secondmodule.use;

import eu.daproject.evl.exception.InvocationException;
import firstmodule.test.TestModule;

public class UseModule {

	public static void main(String[] args) throws Throwable {
			
		System.out.println("Using EVL module");
		
		TestModule tm = new TestModule();
		
		try {
			tm.getMethod().invoke(Integer.valueOf(11));
		}
		catch (InvocationException e) {
			
		}
	}
}
