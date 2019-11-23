package eu.daproject.evl.tomultimethods.part1;

public class B implements I {

	public int foo(String s, int i) {
		if (s.equals("square")) {
			return i * i;
		}
		return 0;
	}

}
