package evl.util;

import java.util.Arrays;

public class MethodClassTuple {

	private Class<?>[] tuple;
	
	public MethodClassTuple(Class<?>[] tuple) {
		this.tuple = tuple;
	}
	
	public Class<?>[] get() {
		return tuple;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(tuple);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}

		MethodClassTuple other = (MethodClassTuple) obj;
		if (!Arrays.equals(tuple, other.tuple)) {
			return false;
		}
		
		return true;
	}

	@Override
	public String toString() {
		String result = "<";
		
		for (int i = 0; i < tuple.length - 1; i++) {
			result += tuple[i].getName() + ", ";
		}
		
		// tuple has size > 0 
		result += tuple[tuple.length - 1].getName() + ">";
		
		return result;
	}
	
}
