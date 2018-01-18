package statStarSophie;

public class StatStar {

	public StatStar(double m, double l, double t, double x, double z) {

		Ms = m * Constants.mSolar; // g
		Ls = l * Constants.lSolar; // erg/s
		Teff = t; // K
		Rs = Math.sqrt(Ls / (4.0 * Math.PI * Constants.sigma)) / (Teff * Teff); // cm
		X = x; // 0.75
		Z = z; // 0.02
		Y = 1.0 - X - Z;
		mu = 1.0 / (2.0 * X + 0.75 * Y + 0.5 * Z);

		P[0] = 0.0;
		T[0] = 0.0;
		dlPdlT[0] = 4.25; // arbitrary value
		M[0] = Ms;
		L[0] = Ls;
		r[0] = Rs;

	}

	private double Ms;
	private double Ls;
	private double Teff;
	private double Rs;
	private double X;
	private double Z;
	private double Y;
	private double mu; // mean molecular weight -> complete ionization

	int rc = 0; // 0: radiation, 1: convection, start with radiation
	double kpad = 0.3; // arbitrary value
	private double tog_bf = 0.01;
	// constant to calculate pressure:
	private double A_fac;

	int n_max;
	double deltaR;

	// Equation of states:
	private double[] T = new double[2];
	private double[] P = new double[2];
	private double[] M = new double[2];
	private double[] L = new double[2];
	private double[] r = new double[2];
	private double[] dlPdlT = new double[2];

	private double rho(int n) {

		double P_gas = P[n] - (4.0 * Constants.sigma / (3.0 * Constants.speedOfLight)) * Math.pow(T[n], 4);
		return (mu * Constants.mH / Constants.boltzmannConstant) * P_gas / T[n];

	}

	private double kappa(int n) {

		tog_bf = (2.82 * Math.pow(rho(n) * (1 + X), 0.2));
		double k_bf = 4.34e+25 / (tog_bf) * Z * (1.0 + X) * rho(n) / Math.pow(T[n], 3.5);
		double k_ff = 3.68e+22 * (1.0 - Z) * (1.0 + X) * rho(n) / Math.pow(T[n], 3.5);
		double k_e = 0.2 * (1.0 + X);

		return (k_bf + k_ff + k_e);

	}

	private double epsilon(int n) {

		double T6 = T[n] * 1e-6;
		double T613 = Math.pow(T6, 1.0 / 3.0);
		double T623 = Math.pow(T6, 2.0 / 3.0);

		double fx = 0.133 * X * Math.sqrt((3.0 + X) * rho(n)) / Math.pow(T6, 1.5);
		double fpp = 1.0 + fx * X;
		double psipp = 1.0 + 1.412e8 * (1.0 / X - 1.0) * Math.exp(-49.98 / T613);
		double Cpp = 1.0 + 0.0123 * T613 + 0.0109 * T623 + 0.000938 * T6;
		double epspp = 2.38e6 * rho(n) * X * X * fpp * psipp * Cpp / T623 * Math.exp(-33.8 / T613);
		double CCNO = 1.0 + 0.0027 * T613 - 0.00778 * T623 - 0.000149 * T6;
		double epsCNO = 8.67e27 * rho(n) * X * Z / 2.0 * CCNO / T623 * Math.exp(-152.28 / T613);

		return (epspp + epsCNO);

	}

	private double dMdr(int n) {

		return 4 * Math.PI * r[n] * r[n] * rho(n);
	}

	private double dPdr(int n) {

		return Constants.gravitationalConstant * M[n] * rho(n) / (r[n] * r[n]);
	}

	private double dLdr(int n) {

		return 4 * Math.PI * rho(n) * r[n] * r[n] * epsilon(n);
	}

	private double dTdr_rad(int n) {

		return (3.0 / (16 * 4 * Math.PI * Constants.sigma)) * rho(n) * kappa(n) * L[n]
				/ (r[n] * r[n] * Math.pow(T[n], 3));
	}

	private double dTdr_conv(int n) {

		return 1.0 / Constants.gamrat * Constants.gravitationalConstant * M[n] / (r[n] * r[n]) * mu * Constants.mH
				/ Constants.boltzmannConstant;
	}

	// calculate surface separately:
	public int Surface() {

		int n;
		deltaR = Rs / 1000;
		n_max = 1000;

		// 0 is previous zone, 1 is new zone

		try {
			for (n = 0; n < 1000; n++) // what is nStart ?!?!?
			{

				r[1] = r[0] - deltaR;

				// mass and luminosity don't change at the surface:
				// M[1] = M[0];
				// L[1] = L[0];

				if (rc == 0) {

					// radiation dominated zone:
					A_fac = 4.34e+25 * Z * (1.0 + X) / tog_bf + 3.68e+22 * (1.0 - Z) * (1.0 + X);

					T[1] = Constants.gravitationalConstant * M[0] * mu * Constants.mH
							/ (4.25 * Constants.boltzmannConstant) * (n + 1) / (Rs * (n_max - (n + 1)));

					// Constants.gravitationalConstant * M[0] * mu * Constants.mH
					// / (4.25 * Constants.boltzmannConstant) * (1.0 / r[1] - 1.0 / Rs);
					// System.out.println(T[1]);

					if (T[1] < 0) {
						throw new IllegalArgumentException();
					}
					P[1] = Math
							.sqrt((1.0 / 4.25) * (16.0 / 3.0 * Math.PI * 4 * Constants.sigma)
									* (Constants.gravitationalConstant * M[0] / L[0])
									* (Constants.boltzmannConstant / (A_fac * mu * Constants.mH)))
							* Math.pow(T[1], 4.25);

					// System.out.println(P[1]);
					if (P[1] < 0) {
						throw new IllegalArgumentException();
					}
				} else {

					// convection dominated zone:
					T[1] = Constants.gravitationalConstant * M[0] * mu * Constants.mH / Constants.gravitationalConstant
							* (1.0 / r[1] - 1.0 / Rs) / Constants.gamrat;
					if (T[1] < 0) {
						throw new IllegalArgumentException();
					}
					P[1] = kpad * Math.pow(T[1], Constants.gamrat);
					if (P[1] < 0) {
						throw new IllegalArgumentException();
					}
				}

				// check if something is negative:
				if (epsilon(1) < 0 || kappa(1) < 0 || rho(1) < 0) {

					throw new IllegalArgumentException();
				}

				// check if the next zone is convevtion or radiation dominated:
				if (n == 0) {
					dlPdlT[1] = dlPdlT[0];
				} else {
					dlPdlT[1] = Math.log(P[1] / P[0]) / Math.log(T[1] / T[0]);
				}

				if (dlPdlT[1] < Constants.gamrat) {
					rc = 1;
				} else {
					rc = 0;
					kpad = P[1] / Math.pow(T[1], Constants.gamrat);
				}

				if (n != 0) {
					// test if deltaM is small enough to assume M[n+1] = M[n]:
					double deltaM = deltaR * 4 * Math.PI * r[0] * r[0] * rho(0);
					M[1] = M[0] - deltaM;
					M[0] = M[1];

					if (Math.abs(deltaM) > (0.001 * Ms)) {

						System.out.println("T_surface: " + T[1]);
						System.out.println("P_surface: " + P[1]);
						System.out.println("Change to main calculation in zone: " + n + '\n');

						// return next zone to be calculated:
						return n + 1;
					}
				}

				// the n+1 values become n-values:
				r[0] = r[1];
				P[0] = P[1];
				T[0] = T[1];
				dlPdlT[0] = dlPdlT[1];

				// System.out.println("n: " + n);
				// System.out.println("T: " + T[1]);
				// System.out.println("P: " + P[1]);

			}
		} catch (IllegalArgumentException e) {

			System.err.println("Temperature, Pressure or Density is negative!");
			return 0;
		}

		System.out.println("T_surface: " + T[1]);
		System.out.println("P_surface: " + P[1]);
		System.out.println("Change to main calculation in zone: " + n + '\n');

		// return next zone to be calculated:
		return n + 1;
	}

	public void mainCalculation(int n) {

		deltaR = Rs / 10000;

		do {

			// Euler Calculation for L,T,M,P:

			r[1] = r[0] - deltaR;
			if (r[1] < 0) {

				System.out.println("Termination Condition: Radius < 0 ! \n");
				break;
			}

			L[1] = L[0] - dLdr(0) * deltaR;
			if (L[1] < 0) {

				System.out.println("Termination Condition: Luminosity < 0 ! \n");
				break;

			}

			if (rc == 0) {
				T[1] = T[0] + dTdr_rad(0) * deltaR;
			} else {
				T[1] = T[0] + dTdr_conv(0) * deltaR;
			}
			if (T[1] < 0) {

				System.out.println("Termination Condition: Temperature < 0 ! \n");
				break;
			}

			M[1] = M[0] - dMdr(0) * deltaR;
			if (M[1] < 0) {

				System.out.println("Termination Condition: Mass < 0 ! \n");
				break;
			}

			P[1] = P[0] + dPdr(0) * deltaR;
			if (P[1] < 0) {

				System.out.println("Termination Condition: Pressure < 0 ! \n");
				break;
			}

			if (rho(1) < 0) {

				System.out.println("Termination Condition: Density < 0 ! \n");
				break;
			}

			// check if next zone is convection or radiation dominated:

			dlPdlT[1] = Math.log(P[1] / P[0]) / Math.log(T[1] / T[0]);

			if (dlPdlT[1] < Constants.gamrat) {
				rc = 1;
			} else {
				rc = 0;
				// kpad = P[n + 1] / Math.pow(T[n + 1], Constants.gamrat);
			}

			n++;
			// new zone becomes previous zone if r > deltaR:

			r[0] = r[1];
			L[0] = L[1];
			T[0] = T[1];
			P[0] = P[1];
			M[0] = M[1];

		} while (r[1] > 0);

		System.out.println("Termination in zone: " + n + "\nwith remaining values in the center: \n");
		System.out.println("Radius: " + r[0] / Rs + " R_star");
		System.out.println("Temperature: " + T[0] + " K");
		System.out.println("Pressure: " + (P[0] * 1E-6) + " bar"); // dyn/cm^2 = e-6 bar
		System.out.println("Density " + rho(0) + " g/cm^3");
		System.out.println("Mass: " + M[0] / Ms + " M_star");
		System.out.println("Luminosity: " + L[0] / Ls + " L_star");

	}

}
