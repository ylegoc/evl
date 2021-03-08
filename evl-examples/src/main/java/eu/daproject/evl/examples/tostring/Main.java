package eu.daproject.evl.examples.tostring;

public class Main {

	public static void main(String[] args) throws Throwable {
		
		System.out.println(ToString.method().invoke(XML.getInstance(), new BigCar()));
		System.out.println(ToString.method().invoke(XML.getInstance(), new SmallDoll("blond")));
		
		SmallTallboy tallboy = new SmallTallboy();
		
		tallboy.add(new BigCar());
		tallboy.add(new SmallDoll("brown"));
		
		System.out.println(ToString.method().invoke(XML.getInstance(), tallboy));
		
	}
}
