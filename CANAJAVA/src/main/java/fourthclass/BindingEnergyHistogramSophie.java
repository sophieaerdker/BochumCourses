package fourthclass;

import org.jlab.groot.data.H2F;
import org.jlab.groot.ui.TCanvas;

public class BindingEnergyHistogramSophie {

	public static double BindingEnergy(int Z, int N) {
		int A = Z + N;
		double a_v = 15.5; // MeV
		double a_s = 16.8;
		double a_c = 0.715;
		double a_A = 23;
		double a_p = 11.3;
		double deltaE;
		double BE;

		if (Z % 2 == 0 && N % 2 == 0) // ee
		{
			deltaE = a_p / Math.sqrt(A);

		} else if (Z % 2 != 0 && N % 2 != 0) // oo
		{
			deltaE = -1 * a_p / Math.sqrt(A);

		} else // eo,oe
		{
			deltaE = 0;
		}

		BE = a_v * A - a_s * Math.pow(A, (2 / 3.0)) - a_c * (Math.pow(Z, 2) / Math.pow(A, (1 / 3.0)))
				- a_A * (Math.pow(Z - N, 2) / A) + deltaE;

		return BE; // MeV
	}

	public static void main(String[] args) {

		H2F ah2F = new H2F("Binding Energy Histogram", 50, 1, 100, 50, 1, 100);

		for (int Z = 1; Z < 50; Z++) {
			for (int N = 1; N < 50; N++) {
				System.out.println(BindingEnergy(Z, N) + "in loop");

				if ((Z + N) < 50) {

					ah2F.setBinContent(Z, N, BindingEnergy(Z, N));
					// System.out.println(BindingEnergy(Z, N));
				}

				else {

					break;
				}
			}
		}
		TCanvas canvas = new TCanvas("Binding Energy", 800, 800);
		canvas.draw(ah2F);

	}

}
