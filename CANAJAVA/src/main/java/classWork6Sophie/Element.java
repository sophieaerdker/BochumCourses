package classWork6Sophie;

public class Element {

	// allows the calculation of Binding Energy for any elements with known A,Z
	// number of Isotopes, maxA and minA are overridden
	// by the specific element classes:
	// specific element class stores information about the known Isotopes,
	// like their max and min masses and the binding energy for the most abundant
	// or an arbitrary Isotope. One could add information about stable isotopes
	// and the lifetime of nonstable isotopes, ...

	private int Z;
	private int A = 0;
	private int N;

	public int getZ() {
		return Z;
	}

	public int getMaxA() {
		return 0;
	}

	public int getMinA() {
		return 0;
	}

	public int getNumberOfIsotopes() {
		return 0;
	}

	public int getA() {
		return A;
	}

	public int getN() {
		return N;
	}

	public double BindingEnergy() {

		if (A == 0) {

			System.err.println("A is not defined!");
			return 0;
		} else {

			double a_v = 15.5; // MeV
			double a_s = 16.8;
			double a_c = 0.715;
			double a_A = 23;
			double a_p = 11.3;
			double deltaE = 0;
			double BE;

			int parity = this.Z % 2 + this.N % 2;

			switch (parity) {

			case 0:
				deltaE = a_p / Math.sqrt(this.A);
				break; // ee
			case 1:
				deltaE = 0;
				break; // eo,oe;
			case 2:
				deltaE = -1 * a_p / Math.sqrt(this.A);
				break; // oo
			}

			BE = (a_v * this.A - a_s * Math.pow(this.A, (2 / 3.0))
					- a_c * (Math.pow(Z, 2) / Math.pow(this.A, (1 / 3.0))) - a_A * (Math.pow(Z - N, 2) / this.A)
					+ deltaE);

			return BE; // MeV
		}
	}

	public double BindingEnergy(int A) {

		double a_v = 15.5; // MeV
		double a_s = 16.8;
		double a_c = 0.715;
		double a_A = 23;
		double a_p = 11.3;
		double deltaE = 0;
		double BE;

		int N = A - this.Z;

		int parity = this.Z % 2 + N % 2;

		switch (parity) {

		case 0:
			deltaE = a_p / Math.sqrt(A);
			break; // ee
		case 1:
			deltaE = 0;
			break; // eo,oe;
		case 2:
			deltaE = -1 * a_p / Math.sqrt(A);
			break; // oo
		}

		BE = (a_v * A - a_s * Math.pow(A, (2 / 3.0)) - a_c * (Math.pow(Z, 2) / Math.pow(A, (1 / 3.0)))
				- a_A * (Math.pow(Z - N, 2) / A) + deltaE);

		return BE; // MeV
	}

	public Element() {

	}

	public Element(int Z) {
		this.Z = Z;

	}

	public Element(int Z, int A) {
		this.Z = Z;
		this.A = A;
		this.N = A - Z;
	}

}
