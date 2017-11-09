package homework5Sophie;

import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.ui.TCanvas;

public class ThermalReaction {

	private double tau_A;
	private double tau_B;
	private double A_0;
	private double Q; // MeV

	public void plotTemperatureTime(double delta_t, double limit_t) {
		GraphErrors gr1 = new GraphErrors();
		ReactionChain reaction2 = new ReactionChain(A_0, tau_A, tau_B);

		gr1.setTitleY("Temperature/thermal Energy [MeV]");
		gr1.setTitleX("Time t [min]");
		gr1.setMarkerColor(2);
		gr1.setMarkerSize(3);
		gr1.setTitle(
				"Thermal Reaction A -> B with half-live " + tau_A + " min, Q-value " + Q + " MeV and A(t=0) = " + A_0);

		for (double t = 0; t < limit_t; t += delta_t) {

			double A = reaction2.amountA(t);
			double T = ((int) (A_0 - A)) * Q;
			gr1.addPoint(t, T, 0, 0);

		}

		TCanvas canvas1 = new TCanvas("Reaction A -> B", 800, 800);
		canvas1.draw(gr1);

	}

	public void plotTemperatureAmountB(double delta_t, double limit_t) {
		GraphErrors gr1 = new GraphErrors();
		ReactionChain reaction2 = new ReactionChain(A_0, tau_A, tau_B);

		gr1.setTitleY("Temperature/thermal Energy [MeV]");
		gr1.setTitleX("Relative Amount of B [%]");
		gr1.setMarkerColor(2);
		gr1.setMarkerSize(3);
		gr1.setTitle(
				"Thermal Reaction A -> B with half-live " + tau_A + " min, Q-value " + Q + " MeV and A(t=0) = " + A_0);

		for (double t = 0; t < limit_t; t += delta_t) {

			double A = reaction2.amountA(t);
			double B = reaction2.amountB(t);
			double T = ((int) (A_0 - A)) * Q;
			double relativeAmount = 100 * (B / A_0);
			gr1.addPoint(relativeAmount, T, 0, 0);
		}

		TCanvas canvas1 = new TCanvas("Reaction A -> B", 800, 800);
		canvas1.draw(gr1);
	}

	public ThermalReaction(double tau_A, double tau_B, double A_0, double Q) {

		this.A_0 = A_0;
		this.tau_A = tau_A;
		this.tau_B = tau_B;
		this.Q = Q;

	}

}
