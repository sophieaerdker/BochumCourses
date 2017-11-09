package homework5Sophie;

import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.ui.TCanvas;

import domain.utils.Constants;

public class ThirdTask {

	public static void main(String[] args) {

		double Q1 = 1.8; // MeV
		double Q2 = 2.3;

		int x = 10;
		double m_A = x * Constants.massOfHydogenInGev / 1000; // in MeV

		double g_A = 1;
		double g_B = 1;
		double g_C = 1;

		EquilibriumReaction reaction = new EquilibriumReaction(Q1, Q2, m_A);
		reaction.setStatisticalValues(g_A, g_B, g_C);
		GraphErrors gr = new GraphErrors();
		TCanvas canvas = new TCanvas("Equilibrium Reaction A+B -> C", 800, 800);

		for (double T = 0.1; T < 10.1; T += 0.1) {

			double A = reaction.relativeAmountAB_C(T);
			gr.addPoint(T, Math.pow(10, -23) * A, 0, 0);

		}

		gr.setTitleX("Temperature [MeV]");
		gr.setTitleY("N_a*N_b/N_c in [10^(23) * 1/m^3]");
		gr.setTitle("Concentration of A,B and C;  Q-value " + Q2 + " MeV");
		gr.setMarkerColor(2);
		gr.setMarkerSize(3);

		canvas.draw(gr);

	}

}
