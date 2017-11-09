package homework5Sophie;

import domain.utils.Constants;

public class EquilibriumReaction {

	private double hQuer = 6.626 * Math.pow(10, -34) / (2 * Math.PI); // Js
	// private double u = Math.pow(1.660, -27); // u to kg
	private double MeVTokg = Math.pow(10, 6) * Constants.eVToJoule / Math.pow(Constants.speedOfLight, 2); // MeV to kg
	private double MeVToJ = Math.pow(10, 6) * Constants.eVToJoule; // MeV to Joule
	private double Q2;

	// private double lambda_C;
	private double g_A;
	private double g_B;
	private double g_C;
	private double m_A;
	private double m_B;
	private double m_C;

	public void setStatisticalValues(double g_A, double g_B, double g_C) {
		this.g_A = g_A;
		this.g_B = g_B;
		this.g_C = g_C;
	}

	public double relativeAmountAB_C(double T) {

		// Na*Nb/Nc in thermal equilibrium
		// with A + B -> C and inverse reaction

		return (g_A * g_B / g_C) * Math.pow((m_A * m_B / m_C) * MeVTokg, 3 / 2)
				* Math.pow(T * MeVToJ / (2 * Math.PI * Math.pow(hQuer, 2)), 3 / 2) * Math.exp(-Q2 / T);
	}

	public EquilibriumReaction(double Q1, double Q2, double m_A) {

		// in MeV:
		this.Q2 = Q2;
		this.m_A = m_A;
		this.m_B = this.m_A - Q1;
		this.m_C = this.m_A + this.m_B - Q2;
	}

}
