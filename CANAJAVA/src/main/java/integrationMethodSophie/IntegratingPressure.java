package integrationMethodSophie;

import domain.utils.Constants;

public class IntegratingPressure {

	static double EulersMethod(double r, double rho, int n) {

		double deltaR = r / n;
		double P = 0;
		double c = 4 * Math.PI / 3 * Constants.gravitationalConstant * rho * rho;

		for (int i = 0; i < n + 1; i++) {

			P += c * r * deltaR;

			r -= deltaR;

		}

		return P;
	}

	static double SimpsonsMethod(double r, double rho, int n) {

		double P = 0;
		double deltaR = r / n;
		double c = 4 * Math.PI / 3 * Constants.gravitationalConstant * rho * rho;

		for (int i = 0; i < n + 1; i++) {

			if (i % 2 == 0) {

				P += 2 * c * (r - i * deltaR);

			} else {

				P += 4 * c * (r - i * deltaR);

			}
		}

		P = P * deltaR / 3;

		return P;
	}

}
