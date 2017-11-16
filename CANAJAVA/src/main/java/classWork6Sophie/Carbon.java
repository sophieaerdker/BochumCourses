package classWork6Sophie;

public class Carbon extends Element {
	private int Z = 6;
	private int maxA = 22;
	private int minA = 8;
	private int A;
	private int N;

	public int getZ() {
		return Z;
	}

	public int getMaxA() {
		return maxA;
	}

	public int getMinA() {
		return minA;
	}

	public int getNumberOfIsotopes() {
		return maxA - minA;
	}

	public int getA() {
		return A;
	}

	public int getN() {
		return N;
	}

	public double BindingEnergy() {

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

		BE = (a_v * this.A - a_s * Math.pow(this.A, (2 / 3.0)) - a_c * (Math.pow(Z, 2) / Math.pow(this.A, (1 / 3.0)))
				- a_A * (Math.pow(Z - N, 2) / this.A) + deltaE);

		return BE; // MeV
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

	public Carbon() {
		// most abundant Isotope:
		super();
		this.A = 12;
		this.N = this.A - Z;

	}

	public Carbon(int A) {
		// arbitrary Isotope:
		super();
		this.A = A;
		this.N = A - Z;

	}

}
