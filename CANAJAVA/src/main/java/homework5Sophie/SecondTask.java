package homework5Sophie;

public class SecondTask {

	public static void main(String[] args) {

		double tau_A = 10.0;
		double tau_B = 0;
		double A_0 = 5; // 10; 100;
		double limit_t = 100;
		double delta_t = 0.5;
		double Q = 1.8; // MeV

		// plot time dependency of A/B:
		// A -> B and B is stable -> no inverse reaction and therefore no equilibrium
		// because simply all of A decays to B with the half-live tau_A

		PlotAmount reaction1 = new PlotAmount(tau_A, tau_B, delta_t, limit_t);
		reaction1.plotAB();

		// to get a temperature dependency:
		// The mass defect is M_a - M_b = 1.8 MeV/c^2, so that the Q value is equal to
		// 1.8MeV and every time A decays to B
		// the energy 1.8MeV is released in form of
		// thermal energy (exotherm), thus the temperature increases, with time t or
		// with the amount of B:

		ThermalReaction reaction2 = new ThermalReaction(tau_A, tau_B, A_0, Q);
		reaction2.plotTemperatureAmountB(delta_t, limit_t);
		reaction2.plotTemperatureTime(delta_t, limit_t);

	}
}
