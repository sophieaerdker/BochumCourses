package integrationMethodSophie;

import domain.utils.Constants;

public class IntegratingPressure {

	static double EulersMethod(double r, double rho, int n) {

		// GraphErrors gr = new GraphErrors();

		double deltaR = r / n;
		double P = 0;
		double c = 4 * Math.PI / 3 * Constants.gravitationalConstant * rho * rho;

		for (int i = 0; i < n - 1 + 1; i++) {

			P += c * r * deltaR;

			// gr.addPoint(r, P, 0, 0);

			r -= deltaR;

		}
		// TCanvas canvas = new TCanvas("Euler Integration", 800, 800);
		// canvas.draw(gr);

		return P;
	}

	static double SimpsonsMethod(double r, double rho, int n) {

		// GraphErrors gr = new GraphErrors();

		double P = 0;
		double deltaR = r / n;
		double c = 4 * Math.PI / 3 * Constants.gravitationalConstant * rho * rho;

		for (int i = 0; i < n + 1; i++) {

			if (i % 2 == 0) {

				P += 2 * c * (r - i * deltaR);
				// gr.addPoint(r - i * deltaR, P * deltaR / 3, 0, 0);

			} else {

				P += 4 * c * (r - i * deltaR);
				// gr.addPoint(r - i * deltaR, P * deltaR / 3, 0, 0);
			}
		}

		P = P * deltaR / 3;
		// TCanvas canvas = new TCanvas("Simpson Integration", 800, 800);
		// canvas.draw(gr, "same");

		return P;
	}

}
