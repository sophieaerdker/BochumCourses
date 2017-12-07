package integrationMethodSophie;

import domain.utils.Constants;

public class CalculationOfPressure {

	static double ExactPressure(double r_0, double rho) {

		double r = 0;
		double c = 4 * Math.PI / 3 * Constants.gravitationalConstant * rho * rho;
		double P = c / 2 * (r_0 * r_0 - r * r);
		return P;
	}

	public static void main(String[] args) {

		int n = 1000;
		double r = 100; // m
		double rho = 1; // kg/m^3

		System.out.println("Pressure inside a sphere with a radius of " + r + "m and a density of " + rho
				+ "kg/m^3 at r=0, calculated with a stepsize of " + r / n + "m:");

		System.out.println("With Euler Method: " + IntegratingPressure.EulersMethod(r, rho, n) + " Pa");
		System.out.println("With Simpsons Method: " + IntegratingPressure.SimpsonsMethod(r, rho, n) + " Pa");
		System.out.println("Exact Pressure: " + ExactPressure(r, rho) + " Pa");

		System.out.println(
				"Error of Euler Method: " + (1 - IntegratingPressure.EulersMethod(r, rho, n) / ExactPressure(r, rho)));
		System.out.println("Error of Simpsons Method: "
				+ (1 - IntegratingPressure.SimpsonsMethod(r, rho, n) / ExactPressure(r, rho)));
	}

}
