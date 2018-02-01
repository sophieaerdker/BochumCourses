package statStarSophie;

import java.util.ArrayList;
import java.util.List;

public class StatStar {

	private List<IntegrationListener> myListeners;

	public StatStar(double m, double l, double t, double x, double z) {

		this.myListeners = new ArrayList<>();

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

	int n_surface;
	int n_main;
	double deltaR;

	// Equation of states:
	private double[] T = new double[2];
	private double[] P = new double[2];
	private double[] M = new double[2];
	private double[] L = new double[2];
	private double[] r = new double[2];
	private double[] dlPdlT = new double[2];

	// termination conditions:

	public double L_min; // in terms of stellar units
	public double R_min;
	public double M_min;

	public void setL_min(double L_min) {
		this.L_min = L_min;
	}

	public void setR_min(double R_min) {
		this.R_min = R_min;
	}

	public void setM_min(double M_min) {
		this.M_min = M_min;
	}

	public void setN(int n) {
		n_main = n;
	}

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

		System.out.println("Starting surface calculation...");

		// if (0.1 * n_main <= 10000) {
		// n_surface = (int) (0.1 * n_main);
		// } else {
		// n_surface = 10000;
		// }

		n_surface = n_main;
		deltaR = Rs / n_surface;
		int i = 0;

		// 0 is previous zone, 1 is new zone

		try {
			for (i = 0; i < 20; i++) // what is nStart ?!?!?(0.1 * n_surface)
			{

				r[1] = r[0] - deltaR;

				// mass and luminosity don't change at the surface:
				// M[1] = M[0];
				// L[1] = L[0];

				if (rc == 0) {

					// radiation dominated zone:
					A_fac = 4.34e+25 * Z * (1.0 + X) / tog_bf + 3.68e+22 * (1.0 - Z) * (1.0 + X);

					T[1] = Constants.gravitationalConstant * M[0] * mu * Constants.mH
							/ (4.25 * Constants.boltzmannConstant) * (i + 1) / (Rs * (n_surface - (i + 1)));

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
				if (i == 0) {
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

				if (i != 0) {
					// test if deltaM is small enough to assume M[n+1] = M[n]:
					double deltaM = deltaR * 4 * Math.PI * r[0] * r[0] * rho(0);
					M[1] = M[0] - deltaM;
					M[0] = M[1];

					if (Math.abs(deltaM) > (0.001 * Ms)) {

						System.out.println("T_surface: " + T[1]);
						System.out.println("P_surface: " + P[1]);
						System.out.println("Change to main calculation in zone: " + i + '\n');

						// return next zone to be calculated:
						return i + 1;
					}
				}

				// the n+1 values become n-values:
				r[0] = r[1];
				P[0] = P[1];
				T[0] = T[1];
				dlPdlT[0] = dlPdlT[1];

				notifyMyListeners((int) ((1 - r[1] / Rs) * 100), false);

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
		System.out.println("Change to main calculation in zone: " + i + '\n');

		// return next zone to be calculated:
		return i + 1;
	}

	public void mainCalculation(int n) {

		deltaR = Rs / n_main;
		System.out.println("Starting Euler method...");

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

			if (r[0] < (R_min * Rs) && L[0] < (L_min * Ls) && M[0] < (M_min * Ms)) {
				System.out.println("Termination Condition: Defined Center reached! \n");
				break;
			}

			notifyMyListeners((int) ((1 - r[1] / Rs) * 100), false);

		} while (r[1] > 0);

		if (r[0] < (R_min * Rs) && L[0] < (L_min * Ls) && M[0] < (M_min * Ms)) {

			notifyMyListeners(100, true);

			double rho_core = M[0] / (4.0 / 3.0 * Math.PI * Math.pow(r[0], 3));
			double P_core = P[0] + 2.0 / 3.0 * Math.PI * Constants.gravitationalConstant * Math.pow(rho_core * r[0], 2);
			double T_core = P_core * mu * Constants.mH / (rho_core * Constants.boltzmannConstant);

			System.out.println("Termination in zone: " + n + "\nwith remaining values in the center: \n");
			System.out.println("Radius: " + r[0] / Rs + " R_star");
			System.out.println("Mass: " + M[0] / Ms + " M_star");
			System.out.println("Luminosity: " + L[0] / Ls + " L_star \n");

			System.out.println("Temperature: " + T_core + " K");
			System.out.println("Pressure: " + (P_core * 1E-6) + " bar"); // dyn/cm^2 = e-6 bar
			System.out.println("Density: " + rho_core + " g/cm^3");
		} else {

			notifyMyListeners(100, true);

			System.err.println("Calculation fails!\n");

			System.out.println("Termination in zone: " + n + "\nwith remaining values in the center: \n");
			System.out.println("Radius: " + r[0] / Rs + " R_star");
			System.out.println("Mass: " + M[0] / Ms + " M_star");
			System.out.println("Luminosity: " + L[0] / Ls + " L_star \n");
		}
		// System.out.println("EnergyProduction: " + epsilon(0));
		// System.out.println("Opacity: " + kappa(0));

	}

	public void RungeKuttaMainCalculation(int n) {

		double P0;
		double P1;
		double P2;
		double P3;
		double M0;
		double M1;
		double M2;
		double M3;
		double L0;
		double L1;
		double L2;
		double L3;
		double T0;
		double T1;
		double T2;
		double T3;

		deltaR = Rs / n_main;

		System.out.println("Starting Runge-Kutta method...");

		do {

			r[1] = r[0] - (deltaR / 2.0);

			P0 = dPdr(0);
			M0 = dMdr(0);
			L0 = dLdr(1);
			if (rc == 0) {
				T0 = dTdr_rad(0);
			} else {
				T0 = dTdr_conv(0);
			}

			// 1. RK
			P[1] = P[0] + P0 * deltaR / 2.0;
			M[1] = M[0] - M0 * deltaR / 2.0;
			L[1] = L[0] - L0 * deltaR / 2.0;
			T[1] = T[0] + T0 * deltaR / 2.0;

			// rho(1), epsilon(1), kappa(1)
			// store results:
			P1 = dPdr(1);
			M1 = dMdr(1);
			L1 = dLdr(1);
			if (rc == 0) {
				T1 = dTdr_rad(1);
			} else {
				T1 = dTdr_conv(1);
			}

			// 2.RK
			P[1] = P[0] + P1 * deltaR / 2.0;
			M[1] = M[0] - M1 * deltaR / 2.0;
			L[1] = L[0] - L1 * deltaR / 2.0;
			T[1] = T[0] + T1 * deltaR / 2.0;

			P2 = dPdr(1);
			M2 = dMdr(1);
			L2 = dLdr(1);
			if (rc == 0) {
				T2 = dTdr_rad(1);
			} else {
				T2 = dTdr_conv(1);
			}

			// 3.RK
			r[1] = r[0] - deltaR;

			P[1] = P[0] + P2 * deltaR;
			M[1] = M[0] - M2 * deltaR;
			L[1] = L[0] - L2 * deltaR;
			T[1] = T[0] + T2 * deltaR;

			P3 = dPdr(1);
			M3 = dMdr(1);
			L3 = dLdr(1);
			if (rc == 0) {
				T3 = dTdr_rad(1);
			} else {
				T3 = dTdr_conv(1);
			}

			// 4. RK:

			P[1] = P[0] + deltaR / 6.0 * (P0 + 2 * P1 + 2 * P2 + P3);
			M[1] = M[0] - deltaR / 6.0 * (M0 + 2 * M1 + 2 * M2 + M3);
			L[1] = L[0] - deltaR / 6.0 * (L0 + 2 * L1 + 2 * L2 + L3);
			T[1] = T[0] + deltaR / 6.0 * (T0 + 2 * T1 + 2 * T2 + T3);

			n++;

			// System.out.println("P " + P[1]);
			// System.out.println("M " + M[1]);
			// System.out.println("L " + L[1]);
			// System.out.println("T " + T[1]);

			r[0] = r[1];
			L[0] = L[1];
			T[0] = T[1];
			P[0] = P[1];
			M[0] = M[1];

			if (r[0] < (R_min * Rs) && L[0] < (L_min * Ls) && M[0] < (M_min * Ms)) {
				System.out.println("Termination Condition: Defined Center reached! \n");
				break;

			} else if (L[0] < 0 || T[0] < 0 || M[0] < 0 || P[0] < 0 || rho(0) < 0 || r[0] < 0) {
				System.err.println("Parameter < 0 !");
				break;
			}

			notifyMyListeners((int) ((1 - r[1] / Rs) * 100), false);

		} while (r[0] > 0);

		if (r[0] < (R_min * Rs) && L[0] < (L_min * Ls) && M[0] < (M_min * Ms))

		{

			notifyMyListeners(100, true);

			double rho_core = M[0] / (4.0 / 3.0 * Math.PI * Math.pow(r[0], 3));
			double P_core = P[0] + 2.0 / 3.0 * Math.PI * Constants.gravitationalConstant * Math.pow(rho_core * r[0], 2);
			double T_core = P_core * mu * Constants.mH / (rho_core * Constants.boltzmannConstant);

			System.out.println("Termination in zone: " + n + "\nwith remaining values in the center: \n");
			System.out.println("Radius: " + r[0] / Rs + " R_star");
			System.out.println("Mass: " + M[0] / Ms + " M_star");
			System.out.println("Luminosity: " + L[0] / Ls + " L_star \n");

			System.out.println("Temperature: " + T_core + " K");
			System.out.println("Pressure: " + (P_core * 1E-6) + " bar"); // dyn/cm^2 = e-6 bar
			System.out.println("Density: " + rho_core + " g/cm^3");
		} else {

			notifyMyListeners(100, true);

			System.err.println("Calculation fails!\n");

			System.out.println("Termination in zone: " + n + "\nwith remaining values in the center: \n");
			System.out.println("Radius: " + r[0] / Rs + " R_star");
			System.out.println("Mass: " + M[0] / Ms + " M_star");
			System.out.println("Luminosity: " + L[0] / Ls + " L_star \n");
		}
	}
	// System.out.println("EnergyProduction: " + epsilon(0));
	// System.out.println("Opacity: " + kappa(0));

	private void notifyMyListeners(int value, boolean finished) {

		IntegrationEvent event = new IntegrationEvent(value, finished);

		for (IntegrationListener IntegrationListener : myListeners) {
			IntegrationListener iEventListener = IntegrationListener;
			iEventListener.nextStep(event);
		}

	}

	public void addListener(IntegrationListener listener) {

		myListeners.add(listener);

	}

	public void removeListener(IntegrationListener listener) {

		myListeners.remove(listener);

	}

}
