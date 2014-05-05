package tutorials.tutorial4;

import tutorials.classes.A;
import tutorials.classes.B;
import tutorials.classes.J;
import tutorials.classes.K;
import evl.base.Method2;
import evl.base.SymmetricComparator;

/**
 * Symmetric and asymmetric dispatch.
 * @author yan
 *
 */
public class Main {
	
	public static void main(String[] args) throws Exception {
		
		B b = new B(1, 4);
		K k = new K(3, 7);
		
		Copier copier = new Copier();
		
		Method2<Void> copy1 = Method2.<Void>builder()
						.build()
						.add(Copier.class.getMethod("copy", A.class, K.class), copier)
						.add(Copier.class.getMethod("copy", B.class, J.class), copier);
		
		copy1.invoke(b, k);
		
		System.out.println(b.getB() + " == " + k.getJ());
		
		
		Method2<Void> copy2 = Method2.<Void>builder()
				.comparator(new SymmetricComparator())
				.build()
				.add(Copier.class.getMethod("copy", A.class, K.class), copier)
				.add(Copier.class.getMethod("copy", B.class, J.class), copier);

		try {
			copy2.invoke(b, k);
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		Method2<Void> copy3 = Method2.<Void>builder()
				.comparator(new SymmetricComparator())
				.build()
				.add(Copier.class.getMethod("copy", A.class, K.class), copier)
				.add(Copier.class.getMethod("copy", B.class, J.class), copier)
				.add(Copier.class.getMethod("copy", B.class, K.class), copier);

		copy3.invoke(b, k);

		System.out.println(b.getA() + " == " + k.getK());
	}
}
